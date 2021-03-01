package de.juli.jobapp.jobweb.web.app;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobweb.service.JobService;
import de.juli.jobapp.jobweb.util.PropertyBean;
import de.juli.jobapp.jobweb.viewmodel.CurrentData;

@Named("list")
@RequestScoped
public class ListDataViewBean extends WebBean {
	private static final long serialVersionUID = 1L;
	private JobService jobService = new JobService();
	private Map<Long, CurrentData> currentJobs;	
	private Integer count;	
	
	public ListDataViewBean() {
	}
	
	@PostConstruct
	public void init() {
		Account account = session.getAccount();
		if(null != account) {
			currentJobs = jobService.getCurrentJobs(account);			
		}
		count = currentJobs.size();
	}

	public String dataView(){
		setCurrentJob();
		return PropertyBean.DETAILS;
	}

	public String dataEdit(){
		setCurrentJob();
		return PropertyBean.EDIT;	
	}

	public String history(){
		setCurrentJob();
		return PropertyBean.HISTORY;	
	}
	
	private void setCurrentJob() {
		String id = getContext().getRequestParameterMap().get("id");
		try {
			session.addContent(PropertyBean.CURRENT_JOB, currentJobs.get(Long.parseLong(id)).getJob());
		} catch (NumberFormatException e) {
			System.err.println(e.getMessage());
		}
	}

	public Map<Long, CurrentData> getCurrentJobs() {
		return currentJobs;
	}

	public Integer getCount() {
		return count;
	}
}
