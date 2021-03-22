package de.juli.jobapp.jobmodel.service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.docuworks.docuhandle.model.DocumentModel;
import de.juli.docuworks.docuhandle.service.DocumentModelService;
import de.juli.docuworks.docuhandle.service.OpenOfficePdfService;
import de.juli.docuworks.docuhandle.service.WebTemplatePdfService;
import de.juli.jobapp.jobmodel.controller.ModelController;
import de.juli.jobapp.jobmodel.converter.DayTimeConverter;
import de.juli.jobapp.jobmodel.enums.FileTyps;
import de.juli.jobapp.jobmodel.enums.Title;
import de.juli.jobapp.jobmodel.exeptions.DocumentServiceExeption;
import de.juli.jobapp.jobmodel.model.AppSetting;
import de.juli.jobapp.jobmodel.model.Document;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.model.Pdf;

public class DocumentService {
	private static final Logger LOG = LoggerFactory.getLogger(DocumentService.class);
	private static Path serverRoot;
	
	/*
	user
	   |_vita
	       |_[lebneslauf_vorlage].pdf
	   |_letter
	       |_[anschreiben_vorlage].odt		
	   |_email
	   |   |_[email_vorlage].odt
	   |   |_[email_vorlage].txt
	   |_applies
	       |_[firmen_name 1]
	       |		|_anschreiben.odt
	       |		|_anschreiben.pdf
	       |		|mail.txt
	       |_[firmen_name 2]
		   |		|_anschreiben.odt
	      u.s.w		|_anschreiben.pdf
	       			|_mail.txt
	*/
	
	/**
	 * Setzt das Wurzelveeichniss (WEB-INF/classes) des aktuellen Servers 
	 */
	public DocumentService() {
		try {
			serverRoot = Paths.get(DocumentService.class.getResource("/").toURI());
			LOG.debug("Serverroot: \n{}", serverRoot);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Aus dem lokalen Serververzeichnis indem die Vorlagen hinterlegt werden der Pfad fuer die 
	 * Firma der Bewerbung erzeugt, um dort dann die generierten Dokumente hintelegen zu können. 
	 */
	public Job createRootDir(Job model) throws IOException {
		Path root = Paths.get(model.getLocalDocDir().trim()).resolve(model.getCompany().getName().trim());
		createDir(root);
		model.setLocalDocDir(root.toString());
		LOG.debug("\n\t{} created", model.getLocalDocDir());
		return model;
	}

	/**
	 * Je nach dem welcher Dokumententyp vorligt bzw. erzeugt werden soll werden hier 
	 * die entsprechenden Methoden zur Generiereung der Dokumente aufgerufen.  
	 */
	public Job createLetter(Job model) {
		FileTyps key = FileTyps.fildByTypeName(model.getLetter().getExtension());
		try {
			switch (key) {
			case DOC:
				createDoc(model);	
				break;
			case DOCX:
				// createDocx(document);
				createOdt(model);
				break;
			case ODT:
				createOdt(model);
				// targetDir = writeLibOficeDoc(source, data);
				break;
			case TXT:
				createTxt(model);
				// targetDir = writeText(source, data);
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return model;
	}
	
	/**
	 * Die zu einer Bewerbung gehoerenden Dokumente / Ordner loeschen
	 */
	public Job delDocuments(Job model) throws IOException {
		String localDocDir = model.getLocalDocDir();
		try {
			if(localDocDir != null && Files.exists(Paths.get(localDocDir))){
				FileUtils.deleteDirectory(new File(localDocDir));				
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new IOException(e);
		}
		model.setLocalDocDir(null);
		model.getEmail().setTarget(null);
		model.getPdf().setTarget(null);
		model.getPdf().setName(null);
		model.getLetter().setTarget(null);
		model.getVita().setTarget(null);
		return model;
	}

	/**
	 * Erstellen des E-Mail Textes fuer die Bewerbung, wenn die Vorlage ein 
	 * Text-Dokument ist. 
	 */
	public Job createEmail(Job model) throws IOException {
		// Mail TXT generieren
		String localDocDir = model.getLocalDocDir();
		Path target = Paths.get(localDocDir);
		Path source = Paths.get(model.getEmail().getTemplate());
		List<String> lines = Files.readAllLines(source, StandardCharsets.ISO_8859_1);
		List<String> outLines = new ArrayList<>();
		for (String line : lines) {
			for (Entry<String, String> e : fillData(model).entrySet()) {
				if (line.contains(String.format("${%s}", e.getKey()))) {
					line = line.replace(String.format("${%s}", e.getKey()), e.getValue());
				}
			}
			outLines.add(line);
		}
		Path mailPath = target.resolve("mail.txt");
		Files.write(mailPath, outLines, StandardCharsets.UTF_8);
		model.getEmail().setTarget(mailPath.toString());
		return model;
	}

	/**
	 * Der Bewergung den hinterlegten und ausgewaehlten Lebenslauf hinzufuegen 
	 */
	public Job createVita(Job model) {
		model.getVita().setTarget(model.getVita().getTemplate());
		return model;
	}

	/**
	 * Mit den Danten aus dem Job - Model wird ein ODT Dokumententemplate mit 
	 * den Modeldaten gefuellt und ein neues ODT Dokument als Anschreiben erzeugt 
	 * und im Bewerbungsordner der Firma gespeichert.   
	 */
	private Job createOdt(Job model) throws Exception {
		Map<String, String> data = fillData(model);
		Path source = Paths.get(model.getLetter().getTemplate());
		Path target = Paths.get(model.getLocalDocDir());
		target = target.resolve(Paths.get("anschreiben" + ".odt"));
		
		LOG.debug("\n\t{}\n\tgoes to\n\t{}", source.toString(), target.toString());
		
		DocumentModelService service = new DocumentModelService();
		DocumentModel documentModel = new DocumentModel(source, target, data);
		documentModel = service.convert(documentModel);		
		model.getLetter().setTarget(target.toString());
		return model;
	}
	
	/**
	 * Eerzeugt eine PDF-Dokument ueber ein Thymleaf Template ueber
	 * den WebTemplatePdfService. Zunaechst werden die Norwendigen Felder in eine HashMap uebertragen und 
	 * die der WebTemplatePdfService generatePdf() Methode uebergeben werden kann. Der Zielpfad fuer die 
	 * generierte Datei wird bestimmt und die Erzeugung des PDF an WebTemplatePdfService unter Angabe 
	 * der zu verwendenen Template-Datei delegiert. Dem Job-Objekt wird das PDF als Anschreiben hinzugefuegt.      
	 * @param templatePath 
	 */
	public Job createWebTemplatePdf(Map<String, String> map, Job model, String templateLoc) {
		WebTemplatePdfService service = new WebTemplatePdfService();
		Path target = Paths.get(model.getLocalDocDir());
		target = target.resolve("anschreiben.pdf");
		target = service.generatePdf(map, target,  templateLoc);
		return addPdf(model, target.toFile().toString());
	}

	/**
	 * Ueber den OpenOfficePdfService das Anschreiben als PDF ueber das hinterlegte und ausgewaelte  
	 * ODT-Template erzeugen. Die entsprechenden Pfade festlegen und zur Uebergabe dieser an das Model die
	 * addPdf() Methode aufrufen.     
	 */
	public Job createOpenOfficePdf(Job model) {
		OpenOfficePdfService service = new OpenOfficePdfService(OpenOfficePdfService.CreationVia.OPEN_OFFICE);
		ModelController contoller = new ModelController();
		AppSetting setting = contoller.findFirst(AppSetting.class);
		boolean success = false;
		
		String source = model.getLetter().getTarget();
		String target = String.format("%s\\%s.%s", model.getLocalDocDir(), "anschreiben", "pdf");
		String cmd = String.format("%s\\%s", setting.getLibreOfficeHome(), setting.getCmd());

		try {
			success = service.create(source, target, cmd);
			if(!success) {
				throw new DocumentServiceExeption("Das Erstellen des PDFs war leider nicht erfogreich!");				
			}
		} catch (DocumentServiceExeption e) {
			throw new DocumentServiceExeption(e.getMessage());
		} catch (Exception e) {
			LOG.error(e.getLocalizedMessage());
			String msg = String.format("Der Service zum Erstellen des PDF konnte nicht ausgefuert werden!\nQuelle: %s\nZiel: %s\n OfficePfad: %s", source, target, cmd);
			throw new DocumentServiceExeption(msg);
		}
		return addPdf(model, target);
	}
	
	/**
	 * Setzt im Model den Pfad zu dem generireten PDF-Dokument, damit er ueber das 
	 * speichern im View-Controller fuer den aktuellen Job persiteirt werden kann.  
	 */
	private Job addPdf (Job model, String target) {
		Pdf pdf = new Pdf();
		pdf.setTarget(target);
		pdf.setExtension("pdf");
		pdf.setName(String.format("%s.%s", model.getCompany().getName(), "pdf"));		
		model.setPdf(pdf);
		return model;
	}

	/**
	 * Erzeugt den Unteroder, das als Ablage fuer die Bewerdbungsdateien geutzt werden soll,
	 * der sich aus dem Benutzer und dem Firmennamen an den die Bewerbung gehen soll 
	 * zusammensetzt.
	 */
	private void createDir(Path path) throws IOException {
		if (!Files.exists(path)) {
			//FileUtils.deleteDirectory(path.toFile());
			Files.createDirectories(path);
		}
	}

	/**
	 * Erzeugt eine Map mit den ganzen Daten fuer die Felder, die in der 
	 * Bewerbung ersetzt werden sollen. 
	 */
	private Map<String, String> fillData(Job model) {
		Map<String, String> data = new HashMap<>();
		DayTimeConverter converter = new DayTimeConverter();
		
		String salary = model.getSalary().toString();
		if(salary == null || salary.isEmpty()) {
			salary = "50000";
		}

		data.put("cmp_name", model.getCompany().getName());
		data.put("cmp_zip", model.getCompany().getZip());
		data.put("cmp_city", model.getCompany().getCity());
		data.put("cmp_street", model.getCompany().getStreet());
		data.put("cmp_addressee", model.getCompany().getAddress());

		data.put("cnt_sex", Title.appellation(model.getCompany().getContact().getTitle()));
		data.put("cnt_title", model.getCompany().getContact().getTitle().getName());
		data.put("cnt_first_name", model.getCompany().getContact().getFirstName());
		data.put("cnt_last_name", model.getCompany().getContact().getLastName());

		data.put("job_title", model.getTitle());
		data.put("job_function", model.getJobfunction());
		data.put("job_salary", salary);
		data.put("job_date", converter.getAsString(model.getJobAdDate()));

		data.put("src_pronomen", model.getSource().getPronomen());
		data.put("src_source", model.getSource().getName());

		data.put("cloud_link", model.getAccount().getProfillink());
		data.put("user_first_name", model.getAccount().getAddress().getFirstName());
		data.put("user_last_name", model.getAccount().getAddress().getLastName());

		return data;
	}
	
	/**
	 * Das Anschreiben der Bewerbgung als Text geneirieren 
	 */
	private void createTxt(Job model) {
		
	}

	/**
	 * Das Anschreiben der Bewerbgung als MS-Word Document generieren 
	 */
	private void createDoc(Job model) {
		
	}

	/**
	 * Das Anschreiben der Bewerbgung als MS-Word Docx generieren 
	 */
	@SuppressWarnings("unused")
	private void createDocx(Document document) {}
}
