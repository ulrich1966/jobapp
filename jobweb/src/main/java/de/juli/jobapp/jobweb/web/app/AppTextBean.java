package de.juli.jobapp.jobweb.web.app;

import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.jboss.weld.exceptions.IllegalArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.enums.JobState;
import de.juli.jobapp.jobmodel.exeptions.ModelNotFoundExeption;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.model.State;
import de.juli.jobapp.jobmodel.service.DocumentService;
import de.juli.jobapp.jobweb.util.AppDirectories;
import de.juli.jobapp.jobweb.util.PropertyBean;
import net.bootsfaces.utils.FacesMessages;

/**
 *  
 */
@Named("apptext")
@RequestScoped
public class AppTextBean extends WebBean {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AppTextBean.class);
	private Map<String, String> txtMap = new HashMap<>(); 
	private Job model;
	private Account account;
	private String txt;
	private DocumentService documentService;
	
	public AppTextBean() {
	}

	/**
	 * Das aktuelle Job-Objekt wird aus der Session-Bean geholt und dann in diesem 
	 * Fuer die Auswahlbox des Bewerbungsstatus wird die entsprechende Eintragsliste
	 * geholt und den Selectboxen zugewiesen.   
	 */
	@PostConstruct
	public void init() {
		super.init();
		try {
			model = (Job) session.getContent(PropertyBean.CURRENT_JOB);
			account = (Account) session.getContent(PropertyBean.ACCOUNT);
			if(model == null) {
				throw new ModelNotFoundExeption("Kein Model vorhanden");
			}
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}
	
	public String getName() {
		return String.format("%s %s", account.getAddress().getFirstName(), account.getAddress().getLastName());
	}

	public String getCity() {
		return String.format("%s %s", account.getAddress().getZip(), account.getAddress().getCity());
	}

	public String getContact() {
		return String.format("%s %s %s", model.getCompany().getContact().getTitle().getName(), model.getCompany().getContact().getFirstName(), model.getCompany().getContact().getLastName());
	}

	public String getCompCity() {
		return String.format("%s %s", model.getCompany().getZip(), model.getCompany().getCity());
	}

	public String getDate() {
		DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT, Locale.GERMANY);
		String formattedDate = df.format(new Date());
		return String.format("%s %s", account.getAddress().getCity(), formattedDate);
	}
	
	/**
	 * Die Rootababen fuer den Job setzen
	 * Bewerbungstext fuer die aktuelle Bewerbung uebernehmen.  
	 */
	public String save() {
		String targetPath = AppDirectories.getTargetPathAsString(getSession().getRoot(), getSession().getAccount().getName());
		model.setLocalDocDir(targetPath);
		try {
			model = getDocumentService().createRootDir(model);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		
		txtMap.put("sender_name", getName());
		txtMap.put("sender_street", account.getAddress().getStreet());
		txtMap.put("sender_city", getCity());
		txtMap.put("sender_phone", String.format("Telefon: %s", account.getAddress().getPhone()));
		txtMap.put("sender_email", String.format("E-Mail: %s", account.getAddress().getMail()));
		txtMap.put("company_name", model.getCompany().getName());
		txtMap.put("company_contact", getContact());
		txtMap.put("company_street", model.getCompany().getStreet());
		txtMap.put("company_city", getCompCity());
		txtMap.put("title", model.getTitle());
		txtMap.put("letter_address", model.getCompany().getContact().getLetterAddress());
		txtMap.put("date", getDate());
		txtMap.put("txt", txt);
		
		model = getDocumentService().createWebTemplatePdf(txtMap, model);	
		try {
			model.addState(new State(JobState.PDF_CREATED));
			model = super.getController().persist(model);
		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesMessages.error(null, "Fehler beim speichern.");
		}

		return PropertyBean.DETAILS;
	}

	/**
	 * Leitet auf die Detail-View weiter  
	 */
	public String details() {
		return PropertyBean.DETAILS;
	}

	public String getTxt() {
		return model.getCompany().getContact().getLetterAddress(); 
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public Job getModel() {
		return model;
	}

	public Account getAccount() {
		return account;
	}

	public DocumentService getDocumentService() {
		if(documentService == null) {
			documentService = new DocumentService();
		}
		return documentService;
	}
}
