package de.juli.jobapp.jobweb.web.app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.enums.JobState;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.model.Source;
import de.juli.jobapp.jobmodel.model.State;
import de.juli.jobapp.jobweb.service.DirService;
import de.juli.jobapp.jobweb.util.AppDirectories;
import de.juli.jobapp.jobweb.util.PropertyBean;
import de.juli.jobapp.jobweb.viewmodel.DocumentSelections;
import net.bootsfaces.utils.FacesMessages;

@Named("createapplication")
@ViewScoped
public class CreateApplication extends WebBean implements CrudBean {
	private static final Logger LOG = LoggerFactory.getLogger(CreateApplication.class);
	private static final long serialVersionUID = 1L;
	private DirService dirService = new DirService();
	private DocumentSelections selections = new DocumentSelections();
	private Date date;
	private Job current_Job;
	
	/**
	 * 4. Schritt Application
	 * Das aktuelle Job-Objekt wird aus der Session-Bean geholt und dann in diesem 
	 * Schritt die Bewerbung mit weitern Kofiguratonsagaben gefuellt.
	 * Fuer die Auswahlboxen weren die entsprechenden Eintragslisten geholt und den Selectboxen zugewiesen   
	 * - Bewerbungsschreiben
	 * - Lebenslauf
	 * - E-Mail
	 * - Fundort
	 * Wenn noch kein Job-Objekt angelegt wurde, gib es einen Fehler und 
	 * es wird auf die Startseite umgeleitet. 
	 */
	@PostConstruct
	public void init() {
		current_Job = (Job) getSession().getContent(PropertyBean.CURRENT_JOB);
		if(current_Job == null) {
			try {
				throw new IllegalStateException("Es gibt kein Job-Objekt");				
			} catch(IllegalStateException e) {
				LOG.error("{}", e.getMessage());
				getSession().addMesssage(new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Das Aufrufen der Konfigurations-Seite hat nicht geklappt!\nEvtl. ist die Bewebung noch nicht angelegt"));
				super.redirect(PropertyBean.APP_HOME);
			}
		}
		try {
			@SuppressWarnings("unchecked")
			List<Source> sources = (List<Source>) getSession().getContent("sources");
			if(sources == null || sources.isEmpty()) {
				sources = getPersistService().getSourceController().findAll();
			}
			getSession().addContent("sources", sources);
			selections.setSources(sources);
		} catch (Exception e1) {
			FacesMessages.error("Fehler");
		}

		Account account = getSession().getAccount();
		Path root = getSession().getRoot();
		Path user = Paths.get(account.getName());
		
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
	
	/**
	 * Die selektierten bzw. eingeragenen Konfigurationsdaten werden dem Job-Objekt hinzugefuegt 
	 * umd die Bewergungserstellung abzuschlie�en wird zur Ueberpruefung der Angaben auf eine
	 * Summerry-Seite weitergeleitet.
	 */
	@Override
	public String create() {
		current_Job.setJobAdDate(new java.sql.Date(getDate().getTime()));
		current_Job.setVita(selections.getSelectedVita());
		current_Job.setLetter(selections.getSelectedLetter());
		current_Job.setEmail(selections.getSelectedEmail());
		current_Job.setSource(selections.getSelectedSource());
		current_Job.addState(new State(JobState.READY_TO_PERSIST, null));
		return PropertyBean.DETAILS;
	}
	
	@Override
	public String read() {
		return null;
	}
	@Override
	public String update() {
		return null;
	}
	@Override
	public String delete() {
		return null;
	}

	public DocumentSelections getSelections() {
		return selections;
	}

	public Job getModel() {
		return current_Job;
	}

	public Date getDate() {
		if(date == null) {
			date = new Date();
		}
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
