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

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

import de.juli.docuworks.docuhandle.service.OpenOfficeFileService;
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
	private static String classes;
	private static String appRoot;
	private static Path userHome;
	private static Path userDir;
	private static Path root;

	public DocumentService() {
		try {
			classes = System.getProperty("java.class.path");
			appRoot = DocumentService.class.getResource("").toURI().toString();
			root = Paths.get(DocumentService.class.getResource("/").toURI());
			userHome = Paths.get(System.getProperty("user.home"));
			userDir = Paths.get(System.getProperty("user.dir"));
			LOG.debug(String.format("root: %s\nuser.home: %s\nuser.dir: %s\njava.class.path: %s\nappRoot: %s", root, userHome, userDir, classes, appRoot));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Aus dem lokalen Serververzeichnis indem die Vorlagen hinterlegt werden den Pfad fuer die 
	 * Firma der Bewerbung erzeugt, um dort dann die generierten die Dokumente hintelegen zu können. 
	 */
	public Job createRootDir(Job model) throws IOException {
		Path root = Paths.get(model.getLocalDocDir().trim()).resolve(model.getCompany().getName().trim());
		createDir(root);
		model.setLocalDocDir(root.toString());
		LOG.debug("\n\t{} created", model.getLocalDocDir());
		return model;
	}

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

	public Job createVita(Job model) {
		model.getVita().setTarget(model.getVita().getTemplate());
		return model;
	}

	private void createTxt(Job model) {
		
	}

	private void createDoc(Job model) {
		
	}
	
	/**
	 * Mit den Danten aus dem Job - Model wird ein ODT Dokumententemplate mit 
	 * den Modeldaten gefuellt und ein neues ODT Dokument als Anschreiben erzeugt 
	 * und im Bewerbungsordner der Firma gespeichert.   
	 */
	private Job createOdt(Job model) throws Exception {
		OpenOfficeFileService oof = new OpenOfficeFileService();
		Map<String, String> data = fillData(model);
		Path source = Paths.get(model.getLetter().getTemplate());
		Path target = Paths.get(model.getLocalDocDir());
		target = target.resolve(Paths.get("anschreiben" + ".odt"));
		LOG.debug("\n\t{}\n\tgoes to\n\t{}", source.toString(), target.toString());
		oof.convert(source, target, data);
		model.getLetter().setTarget(target.toString());
		return model;
	}

	@Deprecated
	public Job createPdf(Job model) throws Exception {
		ModelController contoller = new ModelController();
		AppSetting setting = contoller.findFirst(AppSetting.class);
		String companyName = model.getCompany().getName();
		String source = model.getLetter().getTarget();
		String target = String.format("%s\\%s.%s", model.getLocalDocDir(), "anschreiben", "pdf");

		File inputFile = new File(source);
		File outputFile = new File(target);

		String cmd = String.format("%s\\%s", setting.getLibreOfficeHome(), setting.getCmd());

		ProcessBuilder pb = new ProcessBuilder(cmd, "-headless", "-accept=\"socket,host=127.0.0.1,port=8100;urp;\"", "-nofirststartwizard");
		Process process = pb.start();

		OpenOfficeConnection connection = new SocketOpenOfficeConnection(8100);
		connection.connect();
		DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
		converter.convert(inputFile, outputFile);

		if (connection.isConnected()) {
			connection.disconnect();
		}

		process.destroy();
		pb = null;

		Pdf pdf = new Pdf();
		pdf.setTarget(target);
		pdf.setExtension("pdf");
		pdf.setName(String.format("%s.%s", companyName, "pdf"));
		
		model.setPdf(pdf);

		return model;
	}
	
	/**
	 * Eerzeugt eine PDF-Dokument ueber ein Thymleaf Template ueber
	 * den WebTemplatePdfService  
	 */
	public Job createWebTemplatePdf(Map<String, String> map, Job model) {
		WebTemplatePdfService service = new WebTemplatePdfService();
		Path target = Paths.get(model.getLocalDocDir());
		target = target.resolve("anschreiben.pdf");
		target = service.generatePdf(map, target, root.resolve("templates/anschreiben.html"));
		return addPdf(model, target.toFile().toString());
	}

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
	
	private Job addPdf (Job model, String target) {
		Pdf pdf = new Pdf();
		pdf.setTarget(target);
		pdf.setExtension("pdf");
		pdf.setName(String.format("%s.%s", model.getCompany().getName(), "pdf"));		
		model.setPdf(pdf);
		return model;
	}

	private void createDir(Path path) throws IOException {
		if (Files.exists(path)) {
			FileUtils.deleteDirectory(path.toFile());
		}
		Files.createDirectories(path);
	}

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

		return data;
	}

	@SuppressWarnings("unused")
	private void createDocx(Document document) {}
}
