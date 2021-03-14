package de.juli.jobapp.jobweb.web.app;

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

import de.juli.jobapp.jobmodel.controller.ModelController;
import de.juli.jobapp.jobweb.web.Session;

public abstract class WebBean implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String REDIRECT_TRUE = "faces-redirect=true";
	private ExternalContext context;
	private HttpSession httpSession;
	private HttpServletRequest request;
	private ModelController controller;
	@Inject
	protected Session session;

	public WebBean() {
		context = FacesContext.getCurrentInstance().getExternalContext();
		request = (HttpServletRequest) context.getRequest();
		httpSession = request.getSession(true);
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

	/**
	 * Bevor die View geraendert wird auf eventuelle Fehlermeldungen pruefen
	 */
	public void preRender(ComponentSystemEvent event) {
		if (getSession().getMessageList() != null && !getSession().getMessageList().isEmpty()) {
			for (FacesMessage msg : getSession().getMessageList()) {
				FacesContext.getCurrentInstance().addMessage(null, msg);
				msg.rendered();
				getSession().setMessageList(null);
			}
		}
	}

	/**
	 * Setzt eine Message in den FacesContext
	 * @param msg
	 */
	public void addMsg(FacesMessage msg, String id) {
		FacesContext.getCurrentInstance().addMessage(id, msg);		
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

	public ModelController getController() {
		if(this.controller == null) {
			this.controller = new ModelController();
		}
		return controller;
	}
}
