package de.juli.jobapp.jobweb.web.app;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobweb.util.PropertyBean;

@Named("createjob")
@RequestScoped
public class CreateJob extends WebBean implements CrudBean {
	private static final long serialVersionUID = 1L;
	private Job current_Job;

	public CreateJob() {
	}
	
	/**
	 * 1. Schritt = Job
	 * Eintrittspunk zur Erzeugung einer neuen Bewerbung.
	 * Beim Anlegen einer neuen Bewerbung pruefen ob schon ein Job in der 
	 * Session existiert. Wenn nicht, einen neuen erzeugen und den Account des 
	 * aktuellen Benutzeres fuer den den Job hinzufuegen falls dieser noch 
	 * nicht hinterlegt wurde.
	 */
	@PostConstruct
	public void init() {
		current_Job = new Job();
		current_Job.setAccount(getSession().getAccount());
		getSession().addContent(PropertyBean.JOB, current_Job);
	}

	/**
	 * Wenn das anlegen der neuen Bewerbung auf einen Job auf der View 
	 * bestaetigt wird, wird das Job-Objekt in die Session gegeben und 
	 * steht nunmer hierueber fuer die folgeneden Operationen zur verfuegung. 
	 */
	@Override
	public String create() {
		getSession().addContent(PropertyBean.CURRENT_JOB, current_Job);
		return PropertyBean.COMPANY;
	}

	@Override
	public String read() {
		
		return null;
	}

	@Override
	public String update() {
		return PropertyBean.JOB;
	}

	@Override
	public String delete() {
		
		return null;
	}

	public Job getModel() {
		return current_Job;
	}

	public void setModel(Job model) {
		this.current_Job = model;
	}
}
