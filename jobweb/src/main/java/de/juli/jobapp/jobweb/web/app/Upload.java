package de.juli.jobapp.jobweb.web.app;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobweb.exeptions.ShittHappensExeption;
import de.juli.jobapp.jobweb.service.DirService;
import de.juli.jobapp.jobweb.util.AppDirectories;
import de.juli.jobapp.jobweb.util.PropertyBean;
import de.juli.jobapp.jobweb.viewmodel.DocumentSelections;
import de.juli.jobmodel.enums.DocType;
import de.juli.jobmodel.model.Account;
import net.bootsfaces.utils.FacesMessages;

@Named("upload")
@RequestScoped
public class Upload extends WebBean {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(Upload.class);
	private DirService dirService = new DirService();
	private DocumentSelections selections = new DocumentSelections();
	private Path user;
	private Path root;
	private Part vitaFile;
	private Part letterFile;
	private Part emailFile;
	private Account account;
	//private AccountController controller;	

	public Upload() {
	}
	
	@PostConstruct
	public void init() {
		account = getSession().getAccount();
		root = getSession().getRoot();
		user = Paths.get(account.getName());
		try {
			Path vitaPath = AppDirectories.getVitaPath(root, user);
			selections.setVitas(dirService.vitaDir(vitaPath));
			
			Path letterPath = AppDirectories.getLetterPath(root, user);
			selections.setLetters(dirService.letterDir(letterPath));
			
			Path emailPath = AppDirectories.getEmailPath(root, user);
			selections.setEmails(dirService.emailDir(emailPath));
		} catch (IOException e) {
			FacesMessages.error("Aulisten der Dateien fehlgeschlagen");
		}
	}

	public String vita() {
		try {
			Path path = AppDirectories.getVitaPath(root, user);
			path = createDir(path);
			path = save(path, vitaFile);
			persist(path, DocType.VITA);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return PropertyBean.UPLOAD;
	}

	public String letter() {
		try {
			Path path = AppDirectories.getLetterPath(root, user);
			path = createDir(path);
			path = save(path, letterFile);
			persist(path, DocType.LETTER);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return PropertyBean.UPLOAD;
	}

	public String email() {
		try {
			Path path = AppDirectories.getEmailPath(root, user);
			path = createDir(path);
			path = save(path, emailFile);
			persist(path, DocType.EMAIL);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return PropertyBean.UPLOAD;
	}

	private Path createDir(Path path) throws IOException {
		path = Files.createDirectories(path);
		return path;
	}

	private Path save(Path path, Part file) throws IOException {
		Path target = null;
		if (file != null) {
			InputStream input = file.getInputStream();
			target = new File(path.toString(), file.getSubmittedFileName()).toPath();
			if(target.toFile().exists()) {
				Files.delete(target);
			}
			Files.copy(input, target);
		} else {
			try {
				throw new ShittHappensExeption(this.getClass().getName() + "save(Path path) -> file is NULL!");				
			} catch (ShittHappensExeption e) {
				LOG.error(e.getMessage());
				FacesMessages.error(null, "Es wurde keine Datei ausgew�hlt!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return target; 
	}
	
	private boolean persist(Path path, DocType docType) {
		boolean success = true;
//		if(path != null) {
//			Document doc = new Document(docType, path.toString());
//			if(!account.getDocs().contains(doc)) {
//				account.addDoc(doc);
//				controller.persist(account);
//				success = true;
//			} else {
//				System.err.println(this.getClass().getName() + "persist(Path path, DocType docType) -> Entry [" + path + "] already existiert in DB");
//			}
//		} else {
//			throw new IllegalArgumentException(this.getClass().getName() + "persist(Path path, DocType docType) -> path is NULL!");			
//		}
		return success;
	}
	
	public Account getAccount() {
		return account;
	}

	public Part getVitaFile() {
		return vitaFile;
	}

	public void setVitaFile(Part vitaFile) {
		this.vitaFile = vitaFile;
	}

	public Part getLetterFile() {
		return letterFile;
	}

	public void setLetterFile(Part letterFile) {
		this.letterFile = letterFile;
	}

	public Part getEmailFile() {
		return emailFile;
	}

	public void setEmailFile(Part emailFile) {
		this.emailFile = emailFile;
	}

	public DocumentSelections getSelections() {
		return selections;
	}

	public Path getUser() {
		return user;
	}

	public Path getRoot() {
		return root;
	}
	
	
}
