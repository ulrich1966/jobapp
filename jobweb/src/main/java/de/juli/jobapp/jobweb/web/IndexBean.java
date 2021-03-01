package de.juli.jobapp.jobweb.web;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import de.juli.jobapp.jobweb.util.PropertyBean;
import de.juli.jobapp.jobweb.util.SetUpJobs;

@Named("home")
@RequestScoped
public class IndexBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String name = "";
	@Inject
	private Session session;
	
	public IndexBean() {
	}
	
	@PostConstruct
	public void init() {
		if(session.getLogin()) {
			name = session.getAccount().getName();	
			session.addContent(PropertyBean.LOGIN_NAME, name);
		}
	}

	public void init(ComponentSystemEvent event) {
		
	}
	
	public String setUp() {
		SetUpJobs setUp = new SetUpJobs();
		setUp.create();
		return PropertyBean.HOME;
	}
	
	public Boolean getRenderStart() {
		if(session.getContent("webConsoleUrl") != null && !session.getLogin()) {
			return false;
		}
		return true;
	}
	
	public String getWebConsoleUrl() {
		if(getSession().getContent("webConsoleUrl") != null) {
			return getSession().getContentAsString("webConsoleUrl");
		}
		return "";
	}
	
	public Session getSession() {
		return session;
	}
	
	public void setSession(Session session) {
		this.session = session;
	}

	public String getName() {
		return name;
	}
}
