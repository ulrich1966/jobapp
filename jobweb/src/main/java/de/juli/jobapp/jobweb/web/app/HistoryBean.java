package de.juli.jobapp.jobweb.web.app;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.jboss.weld.exceptions.IllegalArgumentException;

import de.juli.jobapp.jobmodel.enums.AppHistory;
import de.juli.jobapp.jobmodel.model.History;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobweb.util.PropertyBean;

@Named("history")
@RequestScoped
public class HistoryBean extends WebBean {
	private static final long serialVersionUID = 1L;
	private Job model;
	private AppHistory selected;
	private AppHistory[] sbEntries;	
	private String note;
	
	public HistoryBean() {
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
			if(model == null) {
				throw new IllegalArgumentException("Kein Model vorhanden");
			}
			sbEntries = AppHistory.values();
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
	}
	
	
	/**
	 * Die in der Selektbox gewaehlte Statusaenderung wird uebernommen, dem Job-Objekt zugewiesen und 
	 * persistiert.  
	 */
	public String update() {
		if(getSelected() != null && getSelected() != model.getHistoryLastEntry().getAppHistory()) {
			History history = new History(getSelected());
			history.setNote(note);
			model.addHistory(history);
			model = (Job) super.getController().<Job>update(model);
		}
		return "";
	}

	public Job getModel() {
		return model;
	}
	
	public String time() {
		return "";
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public AppHistory[] getSbEntries() {
		return sbEntries;
	}

	public AppHistory getSelected() {
		return selected;
	}
}
