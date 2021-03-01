package de.juli.jobapp.jobweb.web.app;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobweb.util.PropertyBean;
import de.juli.jobmodel.enums.Title;
import de.juli.jobmodel.model.Contact;
import de.juli.jobmodel.model.Job;

@Named("createcontact")
@RequestScoped
public class CreateContact extends WebBean implements CrudBean {
	private static final Logger LOG = LoggerFactory.getLogger(CreateContact.class);
	private static final long serialVersionUID = 1L;
	private Contact model;
	private Job currentJob;
	private Title selectedTitle;
	private Title[] titles;
	
	/**
	 * 3. Schritt Contact
	 * Das aktuelle Job-Objekt wird aus der Session geholt und es wird ggf. ein neues
	 * Contact-Objekt erzeugt. Fuer die Auswahlbox der Titel fuer die Anrede werden
	 * die Titel aus der enum Title eimen String-Array zugewiesen. 
	 * Wenn noch kein Job-Objekt angelegt wurde, gib es einen Fehler und 
	 * es wird auf die Startseite umgeleitet. 
	 */
	@PostConstruct
	public void init() {
		currentJob = (Job) getSession().getContent(PropertyBean.CURRENT_JOB);
		titles = Title.values();
		if(currentJob == null) {
			try {
				throw new IllegalStateException("Es gibt kein Job-Objekt");				
			} catch(IllegalStateException e) {
				LOG.error("{}", e.getMessage());
				getSession().addMesssage(new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Das Anlegen des Kontakts hat nicht geklappt!\nEvtl. ist die Bewebung noch nicht angelegt"));
				super.redirect(PropertyBean.APP_HOME);
			}
		}
		if(model == null) {
			model = new Contact();
		}
	}
	
	/**
	 * In das Contact-Obket wird der ausgewaelte Titel gesetzt und der aktuelle Kontakt wird 
	 * dem Company-Objekt hinzufgefuegt. Dann wird fuer die Konfigurationsangaben zur Applcation 
	 * weitergeleitet.  
	 * Wenn es kein kein Company-Objekt gibt, gibt es einen Fehler und es wird auf die 
	 * Strartseite umgeleitet.  
	 */
	@Override
	public String create() {
		if(currentJob.getCompany() == null) {
			try {
				throw new IllegalStateException("Es gibt kein Company-Objekt im Job-Objekt");				
			} catch(IllegalStateException e) {
				LOG.error("{}", e.getMessage());
				getSession().addMesssage(new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Das Anlegen des Kontakts hat nicht geklappt!\nEs wurde noch keine Firma angelegt."));
				super.redirect(PropertyBean.APP_HOME);
			}
		}
		model.setTitle(selectedTitle);
		currentJob.getCompany().setContact(model);
		return PropertyBean.APPLICATION;
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

	public Contact getModel() {
		return model;
	}

	public void setModel(Contact model) {
		this.model = model;
	}

	public Title[] getTitles() {
		return titles;
	}

	public void setTitles(Title[] titles) {
		this.titles = titles;
	}

	public Title getSelectedTitle() {
		return selectedTitle;
	}

	public void setSelectedTitle(Title selectedTitle) {
		this.selectedTitle = selectedTitle;
	}
}
