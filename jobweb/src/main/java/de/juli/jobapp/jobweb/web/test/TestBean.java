package de.juli.jobapp.jobweb.web.test;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import de.juli.jobapp.jobweb.web.Session;

@Named("test")
@RequestScoped
public class TestBean implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private Session session;
	
	public void test(ComponentSystemEvent event){
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Hallo out of Form init"));
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "", "Hallo out of Form init"));
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Hallo out of Form init"));
	}
	
	@PostConstruct
	public void init() {	
	}
}
