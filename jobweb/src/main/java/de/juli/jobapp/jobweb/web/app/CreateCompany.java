package de.juli.jobapp.jobweb.web.app;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.model.Company;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobweb.util.PropertyBean;

@Named("createCompany")
@RequestScoped
public class CreateCompany extends WebBean implements CrudBean {
	private static final Logger LOG = LoggerFactory.getLogger(CreateCompany.class);
	private static final long serialVersionUID = 1L;
	private Company model;
	private Job currentJob;
	
	/**
	 * 2. Schritt Company
	 * Das aktuelle Job-Objekt wird aus der Sessin geholt und 
	 * es wir ein neues Companie-Objekt fuer die Bewerbung erzeugt mit 
	 * den Daten der Firma an die die Bewerbung gehen soll.
	 * Wenn noch kein Job-Objekt angelegt wurde, gib es einen Fehler und 
	 * es wird auf die Startseite umgeleitet. 
	 */
	@PostConstruct
	public void init() {
		currentJob = (Job) getSession().getContent(PropertyBean.CURRENT_JOB);
		if(currentJob == null) {
			try {
				throw new IllegalStateException("Es gibt kein Job-Objekt");				
			} catch(IllegalStateException e) {
				LOG.error("{}", e.getMessage());
				getSession().addMesssage(new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Das Anlegen der Firma hat nicht geklappt.\nDu musst nochmal anfangen!"));
				super.redirect(PropertyBean.APP_HOME);
			}
		}
		if(model == null) {
			model = new Company();
		}
	}
	
	/**
	 * Dem Job-Objekt das neue Companie-Objekt wird im  Job-Objekt bereitgstellt. 
	 * Weiterleiten fueu den Kontakt.
	 */
	@Override
	public String create() {
		currentJob.setCompany(model);
		return PropertyBean.CONTACT;
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

	public Company getModel() {
		return model;
	}

	public void setModel(Company model) {
		this.model = model;
	}
}
