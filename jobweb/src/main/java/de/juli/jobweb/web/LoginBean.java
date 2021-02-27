package de.juli.jobweb.web;

import java.io.IOException;
import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;

import de.juli.jobmodel.controller.AccountController;
import de.juli.jobmodel.model.Account;
import de.juli.jobweb.util.PropertyBean;

@Named("login")
@RequestScoped
public class LoginBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private AccountController controller = new AccountController();
	private String username;
	private String password;
	@Inject
	private Session session;
	
	
	/**
	 * Bevor die View geraendert wird ...  
	 */
	public void preRender(ComponentSystemEvent event){
		this.username = "uli";
		this.password = "uli";
		login();
	}

	/**
	 * Benztzer suchen und einloggen. Den Autentifizierten Accound in die Session geben und
	 * zur Startseite weiterleiten.
	 */
	public String login() {
		Account account = controller.findByName(username);
		if (account.getName().equalsIgnoreCase(username) && account.getPass().equalsIgnoreCase(password)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Congratulations! You've successfully logged in.");
			FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
			session.setAccount(account);
			session.setLogin(true);
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(String.format("app/%s", PropertyBean.APP_HOME));
			} catch (IOException e) {
				return PropertyBean.HOME;				
			}
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "That's the wrong password. Hint: BootsFaces rocks!");
			FacesContext.getCurrentInstance().addMessage("loginForm:password", msg);
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
	

}
