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

import de.juli.jobapp.jobmodel.enums.Uml;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobweb.exeptions.ShitHappendsExeption;
import de.juli.jobapp.jobweb.service.DirService;
import de.juli.jobapp.jobweb.util.AppDirectories;
import de.juli.jobapp.jobweb.util.PropertyBean;
import de.juli.jobapp.jobweb.viewmodel.DocumentSelections;
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
	
	/**
	 * Nauch Aufruf der View wird der Server nach Vorhandensein der Uploaddatein untersucht
	 * und der Pfad  
	 */
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
			FacesMessages.error("Auflisten der Dateien fehlgeschlagen");
		}
	}

	public String vita() {
		try {
			Path path = AppDirectories.getVitaPath(root, user);
			path = createDir(path);
			path = save(path, vitaFile);
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
				throw new ShitHappendsExeption(this.getClass().getName() + "save(Path path) -> file is NULL!");				
			} catch (ShitHappendsExeption e) {
				LOG.error(e.getMessage());
				FacesMessages.error(null, "Es wurde keine Datei ausgew"+Uml.a_UML.getUchar()+"hlt!");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return target; 
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
