package de.juli.jobfxclient.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.stream.Collectors;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobfxclient.AppStart;
import de.juli.jobfxclient.controller.ModelController;
import de.juli.jobfxclient.enumeration.FileTyps;
import de.juli.jobfxclient.util.AppProperties;
import de.juli.jobfxclient.util.CurrentAppState;
import de.juli.jobmodel.model.Document;
import javafx.stage.DirectoryChooser;
import net.sf.jooreports.templates.DocumentTemplate;
import net.sf.jooreports.templates.DocumentTemplateFactory;

public class ApplyService {
	private static final Logger LOG = LoggerFactory.getLogger(ApplyService.class);
	private ModelController mc;
	private String doc;
	private String pdf;
	private String mail;
	private static Path root;
	private static Path emailSource;
	private Properties prop =PropertyService.findAppProperties();

	/**
	 * Konstruktor
	 * @throws Exception
	 */
	public ApplyService() throws Exception {
		mc = ModelController.getInstance();
		root = Paths.get(new URI(AppProperties.getRootName()));
		emailSource = root.resolve("appdocs");
	}

	/**
	 * Steuerung Docs erstellen 
	 * @return
	 * @throws Exception
	 */
	public CurrentAppState createDocs() throws Exception {
		doc = "";
		pdf = "";
		mail = "";
		CurrentAppState ops = CurrentAppState.FAIL;
		Path targetDir = null;
		Document doc = mc.findDocument();
		FileTyps key = FileTyps.fildByTypeName(doc.getExtension());
		Map<String, String> data = mc.findApplyData();
		data.put("droplink", prop.getProperty("profillink"));
		Path source = null;
		if (doc.getWeblink() != null) {
			handleWebDoc(doc.getWeblink());
		} else {
			source = Paths.get(root + doc.getTemplate());
		}
		try {
			switch (key) {
			case DOC:
				targetDir = writeMsWordDoc(source, data);
				break;
			case DOCX:
				break;
			case ODT:
				targetDir = writeLibOficeDoc(source, data);
				break;
			case TXT:
				targetDir = writeText(source, data);
				break;
			default:
				break;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new FileNotFoundException(String.format("Fehler beim Einlesen von %s", doc.getName()));
		}
		if (targetDir != null) {
			if (AppProperties.getOdtMailFlag().getValue()) {
				ops = writeMail(targetDir, data);
			}
			if (AppProperties.getTxtMailFlag().getValue()) {
				ops = writeTxtMail(targetDir, data);
			}
			mc.setLocalDocDir(targetDir);
		} else {
			ops = CurrentAppState.ABORT;
		}
		String msg = String.format("%s erstellt\n%s erstellt\n%s erstellt", this.doc, this.pdf, this.mail);
		ops.setMsg(msg);
		LOG.info(ops.getMsg());
		return ops;
	}

	/**
	 * Docs erstellen 
	 * @param source
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private Path writeText(Path source, Map<String, String> data) throws Exception {
		Path target = findTargetDir(data.get("name"));
		return writeText(source, target, data);
	}

	/**
	 * Txt erstellen
	 * @param source
	 * @param target
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private Path writeText(Path source, Path target, Map<String, String> data) throws Exception {
		Files.createFile(target);
		if (Files.exists(target)) {
			List<String> list = Files.readAllLines(source, StandardCharsets.ISO_8859_1).stream().map(l -> checkLine(l, data)).collect(Collectors.toList());
			if (list != null && !list.isEmpty()) {
				Files.write(target, list);
			} else {
				LOG.error("Fehler beim Erstellen der Datei: {}", target);
			}
		} else {
			LOG.error("Pad nach: {} existiert nicht", target);
		}
		return target;		
	}

	/**
	 * odt / pdf erstellen
	 * @param source
	 * @param data
	 * @return
	 */
	private Path writeLibOficeDoc(Path source, Map<String, String> data) {
		Path targetDir = null;
		try {
			DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
			DocumentTemplate template = documentTemplateFactory.getTemplate(source.toFile());
			targetDir = findTargetDir(data.get("name"));
			if (targetDir == null) {
				LOG.info("Select directory aborted");
				return null;
			}
			if (Files.exists(targetDir)) {
				FileUtils.deleteDirectory(targetDir.toFile());
				LOG.info(String.format("Directory: %s removed!", targetDir));
			}
			targetDir = Files.createDirectory(targetDir);
			Path createtDoc = targetDir.resolve(Paths.get("anschreiben.odt"));
			template.createDocument(data, new FileOutputStream(createtDoc.toFile()));
			doc = createtDoc.toString();
			Path pdfTarget = targetDir.resolve(Paths.get("anschreiben.pdf"));
			OdtPdfConverterService.getInstance().convert(createtDoc, pdfTarget);
			pdf = pdfTarget.toString();
		} catch (Exception e) {
			e.printStackTrace();
			if (LOG.isDebugEnabled()) {
				System.exit(1);
			}
		}
		return targetDir;
	}

	/**
	 * ms doc erstellen
	 * @param source
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private Path writeMsWordDoc(Path source, Map<String, String> data) throws Exception {
		return null;
	}

	/**
	 * Mail erstellen
	 * @param targetDir
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private CurrentAppState writeMail(Path targetDir, Map<String, String> data) throws Exception {
		CurrentAppState ops = CurrentAppState.FAIL;
		Path source = emailSource.resolve("mail.odt");
		DocumentTemplateFactory documentTemplateFactory = new DocumentTemplateFactory();
		DocumentTemplate template = documentTemplateFactory.getTemplate(source.toFile());
		Path targetDoc = Files.createFile(Paths.get(String.format("%s/%s", targetDir, "email.odt")));
		if (Files.exists(targetDoc)) {
			template.createDocument(data, new FileOutputStream(targetDoc.toFile()));
			targetDoc.toFile().renameTo(new File("mail.txt"));
			mail = targetDoc.toString();
			ops = CurrentAppState.SUCCESS;
		} else {
			LOG.error("Pad nach: {} existiert nicht", targetDoc);
			ops = CurrentAppState.FAIL;
		}
		return ops;
	}

	/**
	 * Texte fuer Mail
	 * @param targetDir
	 * @param data
	 * @return
	 * @throws Exception
	 */
	private CurrentAppState writeTxtMail(Path targetDir, Map<String, String> data) throws Exception {
		CurrentAppState ops = CurrentAppState.FAIL;
		Path source = emailSource.resolve("mail.txt");
		Path target = Paths.get(String.format("%s/%s", targetDir, "email.txt"));
		target = writeText(source, target, data);
		if (target != null) {
			ops = CurrentAppState.SUCCESS;
			mail = target.toString();
		} else {
			ops = CurrentAppState.FAIL;			
		}
		return ops;
	}

	private Path findTargetDir(String newDirName) {
		Path path = null;
		DirectoryChooser chooser = new DirectoryChooser();
		File root = chooser.showDialog(AppStart.getWindow());
		if (root != null) {
			path = Paths.get(String.format("%s/%s", root, newDirName));
		}
		return path;
	}

	private String checkLine(String line, Map<String, String> data) {
		String check = null;
		for (Entry<String, String> entry : data.entrySet()) {
			check = String.format("${%s}", entry.getKey());
			if (line.contains(check)) {
				line = line.replace(check, entry.getValue());
			}
		}
		return line;
	}

	private Path handleWebDoc(String webPath) {
		return null;
	}
}
