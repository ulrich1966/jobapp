package de.juli.jobapp.jobweb.web.app;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.enums.JobState;
import de.juli.jobapp.jobmodel.enums.Title;
import de.juli.jobapp.jobmodel.enums.Uml;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.model.Source;
import de.juli.jobapp.jobmodel.model.State;
import de.juli.jobapp.jobweb.service.DirService;
import de.juli.jobapp.jobweb.util.AppDirectories;
import de.juli.jobapp.jobweb.util.PropertyBean;
import de.juli.jobapp.jobweb.viewmodel.DocumentSelections;
import net.bootsfaces.utils.FacesMessages;


@Named("edit")
@RequestScoped
public class EditDataBean extends WebBean implements CrudBean {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(EditDataBean.class);
	private DirService dirService = new DirService();
	private DocumentSelections selections = new DocumentSelections();
	private Job model;
	private Title[] titles;
	
	public EditDataBean() {
	}

	/**
	 * Zum editieren des Job-Objekts, wir es aus der Session geholt und die Daten fuer die Select-Boxen beschafft.
	 * Wenn noch kein Job-Objekt angelegt wurde, gib es einen Fehler und 
	 * es wird auf die List Seite umgeleitet. 
	 */
	@PostConstruct
	public void init() {
		try {
			model = (Job) session.getContent(PropertyBean.CURRENT_JOB);
			if(getModel() == null) {
				try {
					throw new IllegalStateException("Es gibt kein Job-Objekt");				
				} catch(IllegalStateException e) {
					LOG.error("{}", e.getMessage());
					getSession().addMesssage(new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Das Aufrufen der Edit-Seite hat nicht geklappt!\nEvtl. ist die Bewebung noch nicht angelegt"));
					super.redirect(PropertyBean.LIST);
				}
			}			
			
			List<Source> list = super.getController().findAll(Source.class);
			selections.setSources(list);			
			titles = Title.values();
			
			Path root = getSession().getRoot();
			Path user = Paths.get(getModel().getAccount().getName());
			Path vitaPath = AppDirectories.getVitaPath(root, user);
			selections.setVitas(dirService.vitaDir(vitaPath));
			Path letterPath = AppDirectories.getLetterPath(root, user);
			selections.setLetters(dirService.letterDir(letterPath));
			Path emailPath = AppDirectories.getEmailPath(root, user);
			selections.setEmails(dirService.emailDir(emailPath));
			
		} catch (NumberFormatException | IOException e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * Leitet auf die Detail-Seite weiter
	 * @return
	 */
	public String view() {
		return PropertyBean.DETAILS;	
	}
	
	/**
	 * Persistiert das Job-Objekt mit den geaenderten Angaben und setetzt das 
	 * Objekt wieder in die Session.
	 * Wenn was schief geht wird eine Felhlermeldung ausgegeben und die Seite 
	 * noch mal geraendert. 
	 */
	@Override
	public String update() {
		try {
			model.addState(new State(JobState.UPDATED));
			model = super.getController().persist(getModel());
			getSession().addMesssage(new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Die "+Uml.A_UML.getName()+"nderungen wurden "+Uml.u_UML.getName()+"bernommen!"));
		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesMessages.error(null, "Fehler beim speichern. Die Bewerbung konnte nicht gespeichert werden!");
		}
		return PropertyBean.EDIT;
	}
	

	public String source() {
		return "String SOURCE";	
	}

	public DocumentSelections getSelections() {
		return selections;
	}

	public Title[] getTitles() {
		return titles;
	}

	public DirService getDirService() {
		return dirService;
	}

	public Date getDate() {
		return new Date(getModel().getJobAdDate().getTime());
	}

	public void setDate(Date date) {
		getModel().setJobAdDate(new java.sql.Date(date.getTime()));
	}

	@Override
	public String create() {
		return null;
	}

	@Override
	public String read() {
		return null;
	}

	@Override
	public String delete() {
		return null;
	}

	public Job getModel() {
		return model;
	}

}
