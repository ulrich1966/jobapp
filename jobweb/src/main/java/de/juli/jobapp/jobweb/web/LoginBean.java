package de.juli.jobapp.jobweb.web;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.controller.AccountController;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.util.Md5Handler;
import de.juli.jobapp.jobweb.util.PropertyBean;

@Named("login")
@RequestScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(LoginBean.class);
	private AccountController controller = new AccountController();
	private String username;
	private String password;
	private	Md5Handler md5 = new Md5Handler();
	@Inject
	private Session session;
	
	/**
	 * Bevor die View geraendert wird ...  
	 */
	public void preRender(ComponentSystemEvent event){
		this.username = "uli";
		this.password = "uli";
		//login();
	}

	/**
	 * Benztzer suchen und einloggen. Den Autentifizierten Accound in die Session geben und
	 * zur Startseite weiterleiten.
	 */
	public String login() {
		Account account = null;
		try {
			account = controller.findByName(username);
		} catch (NoResultException e1) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", 
					String.format("Ein Benutzer namens %s wurde nicht gefunden!\nDu solltes Dich regestriern", username));
			FacesContext.getCurrentInstance().addMessage("frm:user", msg);
			LOG.error("{}", e1.getMessage());
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(String.format("app/%s", PropertyBean.HOME));
			} catch (IOException e) {
				LOG.error("{}", e.getMessage());
			}
		}
		if (md5.compareMd5(password, account.getPass())) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", String.format("%s Du wurdest eingeloggt!", username));
			FacesContext.getCurrentInstance().addMessage("msg", msg);
			session.setAccount(account);
			session.setLogin(true);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(String.format("app/%s", PropertyBean.APP_HOME));
			} catch (IOException e) {
				LOG.error("{}", e.getMessage());
			}
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", String.format("Falsches Passwort fuer: %s!", username));
			FacesContext.getCurrentInstance().addMessage("frm:password", msg);
		}
		return PropertyBean.APP_HOME;				
	}

	public String forgotPassword() {
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Default user name: BootsFaces");
		FacesContext.getCurrentInstance().addMessage("loginForm:username", msg);
		msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Default password: rocks!");
		FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
		return PropertyBean.APP_HOME;				
	}

	public String logout() {
		session.logOut();
		return PropertyBean.HOME;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}
	
	public void setUsername(String name) {
		this.username = name;
	}
	
	public String getUsername() {
		return username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public Md5Handler getMd5() {
		return md5;
	}

	public void setMd5(Md5Handler md5) {
		this.md5 = md5;
	}
	

}
