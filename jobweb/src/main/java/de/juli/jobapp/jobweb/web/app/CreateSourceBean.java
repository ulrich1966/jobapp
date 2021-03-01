package de.juli.jobapp.jobweb.web.app;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import de.juli.jobapp.jobmodel.model.Source;
import de.juli.jobapp.jobweb.util.PropertyBean;
import net.bootsfaces.utils.FacesMessages;

@Named("jobsource")
@RequestScoped
public class CreateSourceBean extends WebBean {
	private static final long serialVersionUID = 1L;
	private Source model;
	private Source[] sources;
	private List<Source> sourceList;

	public CreateSourceBean() {
		
	}

	/**
	 * Holt die Source-Objekte aus der Datenbak und weist sie einer Liste zu 
	 */
	@PostConstruct
	public void init() {
		try {
			sourceList = super.getPersistService().getSourceController().findAll();
		} catch (EntityNotFoundException e) {
			FacesMessages.info("messages", "Es sind noch keine Quellen in der Datenbank vorhaden");
			sourceList = new ArrayList<>();
		}
	}

	/**
	 * Die Daten fuer das neue Source-Objekt speichern
	 * Falls es noch kein Liste mit Source-Objekten in der Session gibt, 
	 * wird eine leere neue Liste erstellt. 
	 * 
	 * Mit dem Aufruf von init() wird die Liste mit 
	 * den frischen Daten gefuelt damit auch der neue Datensatz beim update der Data-Table in der View
	 * angezeigt wird!
	 *   
	 * Bei Erfolg wird eine Massage fuer die View generiert und auf die Selbe Seite zuruekgeleitet.
	 * Bei einem Pesistierungsfehler wird eine Exeption geworfen und eine Fehlermeldung 
	 * fuer die View generiert. Wenn die Liste  mit den Source-Objekten verloren ganngen sein 
	 * sollte gibt es auch ein Meldung. 
	 * 
	 */
	public String save() {
		if (getModel().getName() != null && !getModel().getName().isEmpty()) {
			try {
				model = getPersistService().getSourceController().persist(model);
				if(getSession().getContent(PropertyBean.SOURCE) == null) {
					getSession().addContent(PropertyBean.SOURCE, new ArrayList<Source>());
				}
				//((List<Source>) getSession().getContent(PropertyBean.SOURCE)).add(model);					
				init();
			} catch (PersistenceException e) {
				FacesMessages.error("messages", "Fehler beim speichern. Eine gleichnamige Quelle existiert vermutlich bereits.");
			}
			FacesMessages.info("messages", String.format("Quelle [ %s ] hinzugef�gt", model.getName()));
		} else {
			FacesMessages.error("messages", String.format("Source model ist null"));			
		}
		return "";
	}

	public Source getModel() {
		if(model == null) {
			model = new Source();
		}
		return model;
	}

	public void setModel(Source model) {
		this.model = model;
	}

	public Source[] getSources() {
		return sources;
	}

	public void setSources(Source[] sources) {
		this.sources = sources;
	}

	public List<Source> getSourceList() {
		return sourceList;
	}
}
