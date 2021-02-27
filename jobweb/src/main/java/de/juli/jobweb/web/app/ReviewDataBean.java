package de.juli.jobweb.web.app;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.PersistenceException;

import de.juli.jobmodel.enums.JobState;
import de.juli.jobmodel.model.Job;
import de.juli.jobmodel.model.State;
import de.juli.jobweb.util.PropertyBean;
import net.bootsfaces.utils.FacesMessages;

@Named("datareview")
@RequestScoped
@Deprecated
public class ReviewDataBean extends WebBean {
	private static final long serialVersionUID = 1L;
	private Job model;
	
	public ReviewDataBean() {
	}

	@PostConstruct
	public void init() {
		model = (Job) getSession().getContent("job");
	}

	public String edit() {
		return PropertyBean.EDIT;
	}

	public String save() {
		try {
			model.addState(new State(JobState.CREATED));
			model = getPersistService().getJobController().persist(model);
		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesMessages.error("messages", "Fehler beim speichern. Eine gleichnamige Quelle existiert vermutlich bereits.");
		}
		return PropertyBean.REVIEW;
	}

	public Job getModel() {
		return model;
	}	
}
