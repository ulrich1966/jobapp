package de.juli.jobweb.web.app;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import de.juli.jobmodel.service.PersistService;
import de.juli.jobweb.web.Session;

public abstract class WebBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String REDIRECT_TRUE = "faces-redirect=true";
	private PersistService persistService;
	private ExternalContext context;
	private HttpSession httpSession;
	private HttpServletRequest request;
	@Inject
	protected Session session;

	public WebBean() {
		context = FacesContext.getCurrentInstance().getExternalContext();
		request = (HttpServletRequest) context.getRequest();
		httpSession = request.getSession(true);
		persistService = new PersistService();
	}
	
	/**
	 * Bevor die View geraendert wird auf eventuelle Fehlermeldungen 
	 * pruefen
	 */
	public void preRender(ComponentSystemEvent event){
		for (FacesMessage msg : getSession().getMessageList()) {
			FacesContext.getCurrentInstance().addMessage(null, msg);
			msg.rendered();
			getSession().setMessageList(null);
		}
	}

	@PostConstruct
	public void init() {
		try {
			if (!session.getLogin()) {
				kickMeHome();
			}
		} catch (ViewExpiredException e) {
			System.err.println("Session augelaufen");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
		}
	}

	public void redirect(String url) {
		try {
			context.redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void kickMeHome() throws IOException {
		context.redirect(request.getContextPath());
	}

	public ExternalContext getContext() {
		return context;
	}

	public HttpSession getHttpSession() {
		return httpSession;
	}

	public void setHttpSession(HttpSession httpSession) {
		this.httpSession = httpSession;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public Session getSession() {
		return session;
	}

	public PersistService getPersistService() {
		return persistService;
	}

	public void setPersistService(PersistService persistService) {
		this.persistService = persistService;
	}
}
