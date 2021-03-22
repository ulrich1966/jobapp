package de.juli.jobapp.jobweb.web.app;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.enums.AppHistory;
import de.juli.jobapp.jobmodel.enums.JobState;
import de.juli.jobapp.jobmodel.enums.Uml;
import de.juli.jobapp.jobmodel.model.History;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.model.State;
import de.juli.jobapp.jobmodel.service.DocumentService;
import de.juli.jobapp.jobweb.exeptions.ShitHappendsExeption;
import de.juli.jobapp.jobweb.service.SendService;
import de.juli.jobapp.jobweb.util.AppDirectories;
import de.juli.jobapp.jobweb.util.PropertyBean;
import net.bootsfaces.utils.FacesMessages;

@Named("data")
@RequestScoped
public class ViewDataBean extends WebBean {
	private static final Logger LOG = LoggerFactory.getLogger(ViewDataBean.class);
	private static final long serialVersionUID = 1L;
	private DocumentService service = new DocumentService();
	private Boolean docCreated = false;
	private Boolean docDeleted = false;
	private Boolean hasChanged = false;
	private Boolean isNew = false;
	private Boolean dirExist = false;
	private Boolean hasTaplates = false;
	private Boolean readyToSend = false;
	private Boolean hasMailCredentials = false;
	private String modal;
	private String mailUser;
	private String mailPass;
	private Job model;

	public ViewDataBean() {
	}

	/**
	 * Holt das aktuelle Job-Objekt aus der Session stelltes in dieser Bean fuer
	 * die View zur Verfuegung und setze die Satusse zum anzeigen/ausblenden der
	 * Aktion-Butttons. Prueft ob es ein lokales Verzeichnis angelegt wurde.
	 * Gibt es kein Objekt wird eine Fehlermeldung generiert und auf die
	 * Startseite unmgeleitet.
	 */
	@PostConstruct
	public void init() {
		hasChanged = false;
		try {
			model = (Job) session.getContent(PropertyBean.CURRENT_JOB);
			if (model == null) {
				try {
					throw new IllegalStateException("Es gibt kein Job-Objekt");
				} catch (IllegalStateException e) {
					LOG.error("{}", e.getMessage());
					getSession().addMesssage(new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Das Aufrufen der Detail-Seite hat nicht geklappt!\nEvtl. ist die Bewebung noch nicht angelegt"));
					super.redirect(PropertyBean.APP_HOME);
				}
			}

			settingStates(model.getStateLastEntry().getJobState());

			if (model.getLocalDocDir() != null) {
				dirExist = true;
			}

			if (!(model.getEmail() == null && model.getLetter() == null && model.getVita() == null)) {
				hasTaplates = true;
			}

		} catch (IllegalArgumentException e) {
			LOG.error(e.getMessage());
		}

		String msg = makeStateString();
		super.addMsg(new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg), "footer_msg");
	}
	

	/**
	 * Bevor die View geraendert wird soll geprueft werden, ob der Benutzer bereits Zugangsdaten fuer den SMPT E-Mail Versandt 
	 * hinterlegt hat, um ggf. den Modal-Dialog zu rendern. Wenn es noch keine Zugangsdaten in der Session gibt, dann wird die 
	 * Account E-Mail als Usermen als Vorschlag in den mailUser gesetzt, Modal wird auf 'show' gesetzt, damit der Modal-Dialog 
	 * angezeigt wird. HasMail ist dann 'false' damit sendMail ohne Verdand returniert. Sind die Zugangsdaten gesezt, werden 
	 * die entsprechenden Klassenvariablen gesetzt und hasMail ist 'true', damit in sendMail die Mail versandt wird. 
	 */
	public void preRender(ComponentSystemEvent event) {
		super.preRender(event);
		this.hasMailCredentials = checkCredentials();
	}

	/**
	 * Leitet zur Edit-Seite weiter
	 */
	public String edit() {
		return PropertyBean.EDIT;
	}

	/**
	 * Leitet zur Verlaufs-Seite weiter
	 */
	public String history() {
		return PropertyBean.HISTORY;
	}

	/**
	 * Speichert ein neu angegtes Job-Objekt als Bewerbung
	 */
	public String save() {
		try {
			model.addState(new State(JobState.CREATED));
			model = super.getController().persist(model);
			session.getAccount().addJob(model);
		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesMessages.error(null, "Fehler beim speichern.");
		}
		return PropertyBean.DETAILS;
	}

	/**
	 * Loescht den aktuellen Datensatz aus der Datenbank
	 */
	public String delete() {
		// Kommt noch
		// super.getPersistService().getCompanyController().remove(current_job.getCompany());
		return PropertyBean.DETAILS;
	}

	/**
	 * Das Verzeichnis mit den Dokumenten fue die aktuelle Bewerbung loeschen
	 */
	public String deldocs() {
		try {
			service.delDocuments(model);
			model.addState(new State(JobState.DOC_DELETED, "L" + Uml.o_UML.getUchar() + "schung durch Benutzer"));
			model.addHistory(new History(AppHistory.NOT_BUILD, "Die Dokumente wurden von Dir gel" + Uml.o_UML.getUchar() + "scht!"));
			model = super.getController().persist(model);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return PropertyBean.DETAILS;
	}

	/**
	 * Erstellt die Dokumente fuer die Bewerbung durch entsprechenenden
	 * Serviceaufruf im DocumentService, persisteriert das aktuelle Job-Objekt,
	 * aktuallisiert den Status und setzt das Objekt neu in die Session.
	 */
	public String createDocs() {
		try {
			String targetPath = AppDirectories.getTargetPathAsString(getSession().getRoot(), getSession().getAccount().getName());
			String msg = String.format("Die unterlagen wurden erstellt und liegen auf dem Server: %s", targetPath);
			model.setLocalDocDir(targetPath);
			model = service.createRootDir(model);
			model = service.createLetter(model);
			model = service.createEmail(model);
			model = service.createVita(model);
			model = service.createOpenOfficePdf(model);
			model.addState(new State(JobState.DOC_CREATED, "Unterlagen zur Bewerbung wurden generiert"));
			model.addHistory(new History(AppHistory.NOT_SEND, msg));
			model = super.getController().persist(model);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			FacesMessages.error(null, "Fehler beim speichern. Eine gleichnamige Quelle existiert vermutlich bereits.");
		}
		return PropertyBean.DETAILS;
	}

	/**
	 * Uebernahme der Eingaben aus dem Modal-Dialog und weiterleitung zu sendMail, um die Mail abzusenden.
	 * Sind die Credentials nicht gesetzt wird der Modal-Dialog ohne Aktionen geschlossen.   
	 */
	public String doModal() {
		if(null != this.mailUser && null != this.mailPass) {
			super.session.addContent(PropertyBean.MAIL_USER, this.mailUser);						
			super.session.addContent(PropertyBean.MAIL_PASS, this.mailPass);
			return sendMail();
		}
		return "";
	}
	
	/**
	 * Fuer den Versand der E-Mail sind valide Zugangsdaten fuer den SMPT Server notwendig, die zuvor Gesetzt werden muessen.
	 * Ist dieses geschehen werden die Unterlagen per E-Mail mit dem SendService versandt ueber den Aufruf der Methode send().
	 * Diese staretet einen Thread und bei erfogreichem Versandt liefert sie success = true zurueck oder bei Misserfolg false. 
	 * Ist die Mail erfolgreich versendet wird der Status als Versendet gesetze und das aktuelle Job-Objekt persistiert. 
	 * Generiert fuer die Ausgabe entsprechende Erfolgs- oder Fehlermeldungen und setzt bei einem Fehler SMPT Zugansdaten zurueck. 
	 */
	public String sendMail() {
		if(!checkCredentials()) {
			return "";
		}		
		
		try {
			String msg = null;
			boolean success = false;
			SendService service = new SendService(model, this.mailUser, this.mailPass);
			success = service.send();

			if (success) {
				msg = String.format("Die Bewerbung an %s wurde verdandt", model.getCompany().getName());
				model.addHistory(new History(AppHistory.SEND, msg));
				model.addState(new State(JobState.SEND, msg));
				model = super.getController().persist(model);
				session.addMesssage(new FacesMessage(FacesMessage.SEVERITY_INFO, "", msg));
			} else {
				try {
					throw new ShitHappendsExeption("Das versenden der Bewerbung ist schief gelaufen!");
				} catch (ShitHappendsExeption e) {
					LOG.error(e.getMessage());
					session.addMesssage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Fehler beim versenden der E-Mail ggf. stimmen die Zugansdaten nicht!"));
					session.removContent(PropertyBean.MAIL_USER);				
					session.removContent(PropertyBean.MAIL_PASS);				
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
			
		} catch (Exception e2) {
			LOG.error("{}", e2.getMessage());
			session.addMesssage(new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Der E-Mail Versandt wurde unterbrochen!"));
		}
		
		return PropertyBean.DETAILS;
	}

	/**
	 * Leitet zum Erstellen eines Bewerbungstextes weiter
	 */
	public String applyTxt4Pdf() {
		return PropertyBean.TXT;
	}

	private Boolean checkCredentials() {
		boolean cred = false;
		if(null == super.session.getContent(PropertyBean.MAIL_USER) || null == super.session.getContent(PropertyBean.MAIL_PASS)) {
			modal = "show";
			this.mailUser = super.session.getAccount().getSender();
			cred = false;
		} else {
			this.mailUser = super.session.getContentAsString(PropertyBean.MAIL_USER);
			this.mailPass = super.session.getContentAsString(PropertyBean.MAIL_PASS);
			modal = null;
			cred = true;
		}
		return cred;
	}

	/**
	 * Fummelt die Statuse aus dem Model um die Funktion (disabele Attribut) der Schaltflaechen zu setzen fuer eine 
	 * sinnvolle Abfolge der Bewerbung  
	 */
	private void settingStates(JobState jobState) {
		switch (jobState) {
		case CREATED:
			hasChanged = false;
			break;
		case UPDATED:
			hasChanged = true;
			break;
		case READY_TO_PERSIST:
			isNew = true;
			break;
		case DOC_CREATED:
			docCreated = true;
			readyToSend = true;
			break;
		case SEND:
			readyToSend = false;
			break;
		case DOC_DELETED:
			docDeleted = true;
			break;
		default:
			break;
		}
	}

	/**
	 * Bietet eine Uebersicht der aktuellen Satuse als String zur kontolle der
	 * Rednervorgaenge. Ist nur in der Entwicklungphase sinnvoll.
	 */
	private String makeStateString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Dokumente erstellt: ");
		sb.append(docCreated);
		sb.append(" | Dokumente geloescht: ");
		sb.append(docDeleted);
		sb.append(" | Datensatz geaendert: ");
		sb.append(hasChanged);
		sb.append(" | Verzeichnis existiert: ");
		sb.append(dirExist);
		sb.append(" | Datensatz ist neu: ");
		sb.append(isNew);
		sb.append(" | Alle Vorlagen vorhanden: ");
		sb.append(hasTaplates);
		return sb.toString();
	}

	// ---------------------------Getter / Setter ----------------------------//
	
	public Job getModel() {
		return model;
	}

	public Boolean getDirExist() {
		return dirExist;
	}

	public Boolean getDocCreated() {
		return docCreated;
	}

	public Boolean getDocDeleted() {
		return docDeleted;
	}

	public Boolean getHasChanged() {
		return hasChanged;
	}

	public Boolean getIsNew() {
		return isNew;
	}

	public Boolean getHasTaplates() {
		return hasTaplates;
	}

	public String getModal() {
		return modal;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}

	public String getMailPass() {
		return mailPass;
	}

	public void setMailPass(String mailPass) {
		this.mailPass = mailPass;
	}

	public Boolean getReadyToSend() {
		return readyToSend;
	}
}
