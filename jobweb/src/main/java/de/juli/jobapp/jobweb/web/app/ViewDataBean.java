package de.juli.jobapp.jobweb.web.app;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.enums.AppHistory;
import de.juli.jobapp.jobmodel.enums.JobState;
import de.juli.jobapp.jobmodel.model.History;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.model.State;
import de.juli.jobapp.jobmodel.service.DocumentService;
import de.juli.jobapp.jobmodel.service.PersistService;
import de.juli.jobapp.jobweb.exeptions.ShittHappensExeption;
import de.juli.jobapp.jobweb.service.SendService;
import de.juli.jobapp.jobweb.util.AppDirectories;
import de.juli.jobapp.jobweb.util.PropertyBean;
import net.bootsfaces.utils.FacesMessages;

@Named("data")
@RequestScoped
public class ViewDataBean extends WebBean {
	private static final Logger LOG = LoggerFactory.getLogger(ViewDataBean.class);
	private static final long serialVersionUID = 1L;
	private PersistService persistService = new PersistService();
	private DocumentService service = new DocumentService();
	private Boolean docCreated = false;
	private Boolean docDeleted = false;
	private Boolean hasChanged = false;
	private Boolean dirExist = false;
	private Job model;

	public ViewDataBean() {
	}

	/**
	 * Holt das aktuelle Job-Objekt aus der Session stelltes in dieser Bean fuer
	 * die View zur Verfuegung und setze die Satusse zum anzeigen/ausblenden der Aktion-Butttons. 
	 * Prueft ob es ein lokales Verzeichnis angelegt wurde. Gibt es kein Objekt wird eine Fehlermeldung
	 * generiert und auf die Startseite unmgeleitet.
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
			switch (model.getStateLastEntry().getJobState()) {
			case CREATED:
				hasChanged = false;
				break;
			case UPDATED:
				hasChanged = true;
				break;
			case READY_TO_PERSIST:
				hasChanged = true;
				break;
			case DOC_CREATED:
				docCreated = true;
				break;
			case DOC_DELETED:
				docDeleted = true;
				break;
			default:
				break;
			}

			if (model.getLocalDocDir() != null) {
				dirExist = true;
			}

		} catch (IllegalArgumentException e) {
			LOG.error(e.getMessage());
		}
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
	 * Speichert das aktuelle Job-Objekt als Bewerbung
	 */
	public String save() {
		try {
			model.addState(new State(JobState.CREATED));
			model = getPersistService().getJobController().persist(model);
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
		//super.getPersistService().getCompanyController().remove(current_job.getCompany());
		return PropertyBean.DETAILS;
	}

	/**
	 * Das Verzeichnis mit den Dokumenten fue die aktuelle Bewerbung loeschen 
	 */
	public String deldocs() {
		try {
			service.delDocuments(model);
			model.addState(new State(JobState.DOC_DELETED, null));
			model = persistService.getJobController().persist(model);
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		return PropertyBean.DETAILS;
	}

	/**
	 * Erstellt die Dokumente fuer die Bewerbung durch entsprechenenden Serviceaufruf 
	 * im DocumentService, persisteriert das aktuelle Job-Objekt, aktuallisiert den Status 
	 * und setzt das Objekt neu in die Session. 
	 */
	public String createDocs() {
		try {
			model.setLocalDocDir(AppDirectories.getTargetPathAsString(getSession().getRoot(), getSession().getAccount().getName()));
			model = service.createRootDir(model);
			model = service.createLetter(model);
			model = service.createEmail(model);
			model = service.createVita(model);
			model = service.createOpenOfficePdf(model);
			model.addState(new State(JobState.DOC_CREATED, null));
			model.addHistory(new History(AppHistory.NOT_SEND, null));
			model = persistService.getJobController().persist(model);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			FacesMessages.error(null, "Fehler beim speichern. Eine gleichnamige Quelle existiert vermutlich bereits.");
		}
		return PropertyBean.DETAILS;
	}

	/**
	 * Schickt das Job-Objekt zum Verenden an den SendService und setzt des Objekt 
	 * frisch in die Session 
	 */
	public String send() {
		SendService service = new SendService(model);
		boolean success = service.send();
		if (success) {
			session.addContent(PropertyBean.CURRENT_JOB, this.model);
		} else {
			try {
				throw new ShittHappensExeption("Das versenden der Bewerbung ist schief gelaufen!");
			} catch (ShittHappensExeption e) {
				LOG.error(e.getMessage());
				FacesMessages.error(null, "Fehler beim speichern. Eine gleichnamige Quelle existiert vermutlich bereits.");			
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
		return PropertyBean.DETAILS;
	}

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
}
