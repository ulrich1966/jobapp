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
import de.juli.jobapp.jobmodel.util.AppProperties;
import de.juli.jobapp.jobweb.util.AppDirectories;
import de.juli.jobapp.jobweb.util.PropertyBean;
import net.bootsfaces.utils.FacesMessages;

/**
 * Controller zur Erzeugung eines PDF-Anschreibens in der View via Formular. 
 */
@Named("apptext")
@RequestScoped
public class AppTextBean extends WebBean {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AppTextBean.class);
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
	
	/**
	 * Pruefung ob es bereits eien Zielpfad im Job-Objekt gibt und ggf. diesen setzen. Den individuellen Anschreibentext als PDF generieren lassen
	 * und Bewerbungstext fuer die aktuelle Bewerbung uebernehmen und persitieren.  
	 */
	public String save() {
		if(null == model.getLocalDocDir() || !model.getLocalDocDir().isEmpty()) {
			String targetPath = AppDirectories.getTargetPathAsString(getSession().getRoot(), getSession().getAccount().getName());
			model.setLocalDocDir(targetPath);			
		}
		try {
			model = getDocumentService().createRootDir(model);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		model = getDocumentService().createWebTemplatePdf(mapifyValues(), model, AppProperties.getInstance(AppProperties.CONFIG_PROP).propertyFind("thymeleaf.template"));	
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

	/**
	 * Die fuer die PDF-Erzeugung notwendigen Daten aus dem View-Formular bzw. aus dem Job-Objekt in eine Map zur Ubergabe an
	 * den DokumentenService ueberfueren. 
	 */
	private Map<String, String> mapifyValues() {
		Map<String, String> data = new HashMap<>();
		data.put("sender_name", getName());
		data.put("sender_street", account.getAddress().getStreet());
		data.put("sender_city", getCity());
		data.put("sender_phone", account.getAddress().getPhone());
		data.put("sender_email", account.getAddress().getMail());
		data.put("company_name", model.getCompany().getName());
		data.put("company_contact", getContact());
		data.put("company_street", model.getCompany().getStreet());
		data.put("company_city", getCompCity());
		data.put("title", model.getTitle());
		data.put("letter_address", model.getCompany().getContact().getLetterAddress());
		data.put("date", getDate());
		data.put("txt", txt);
		return data;
	}
	
	// ------- Getter / Setter ------------------------------------------
	
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
