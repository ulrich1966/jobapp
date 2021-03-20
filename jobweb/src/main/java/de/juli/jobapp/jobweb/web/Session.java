package de.juli.jobapp.jobweb.web;

import java.io.Serializable;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.controller.EmController;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobweb.util.PropertyBean;

/**
 * Haelt alle wesentlichen Daten, die ueber den Requestscope erhalten werden muessen 
 *
 */
@Named("session")
@SessionScoped
public class Session implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(Session.class);
	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> sessionContent = new HashMap<>() ;
	private List<FacesMessage> messageList = new ArrayList<>();
	private static int init = 0;
	private boolean login;
	private URI rootUri;
	private Path root;
	private String dbUser;
	private String dataBase;
	private String mailPass;
	private String mailUser;
	
	public Session(){
		init++;
		LOG.debug("\n\tSession constructor: {}", init);
	}
	
	/**
	 * Besorgt Informationen zur Datenbank und Root-Path
	 */
	@PostConstruct
	public void init(){
		try {
			rootUri = Session.class.getResource("/").toURI();
			root = Paths.get(rootUri);
			dbUser = EmController.getDbUser();
			dataBase = EmController.getDb();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		LOG.debug(String.format("\n\trootUri: %s \n\troot: %s", rootUri, root));
	}

	/**
	 * Logout
	 */
	public void logOut() {
		login = false;
		sessionContent.remove(PropertyBean.ACCOUNT);
	}
	
	/**
	 * Neues Objekt ind die Session-Map setzten oder vorhandenes ueberschreiben  
	 */
	public void addContent(String key, Object value) {
		sessionContent.put(key, value);
	}

	/**
	 * Ein Objekt aus der Session-Map holen
	 */
	public Object getContent(String key) {
		return sessionContent.get(key);
	}
	
	/**
	 * Ein Objekt aus der Session-Map hohlen und als String zrueckgeben
	 */
	public String getContentAsString(String key) {
		return getContent(key).toString();
	}

	/**
	 * Ein Objekt aus der Session-Map hohlen und als Integer zrueckgeben
	 */
	public Integer getContentAsInt(String key) {
		try {
			return (Integer) getContent(key);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Ein Objekt aus der Session-Map hohlen und als Boolean zrueckgeben
	 */
	public Boolean getContentAsBool(String key) {
		try {
			return (Boolean) getContent(key);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * Ein Objekt aus der der Session-Map entfernen
	 */
	public void removContent(String key) {
		sessionContent.remove(key);
	}

	/**
	 * Die ganze Session-Map bekommen
	 */
	public Map<String, Object> getSessionContent() {
		return sessionContent;
	}

	/**
	 *	Den Account fuer den eingeloggten User in die Session-Map setzten 
	 */
	public void setAccount(Account account) {
		sessionContent.put(PropertyBean.ACCOUNT, account);
	}
	
	/**
	 *	Den Account fuer den eingeloggten User aus der Session-Map holen 
	 */
	public Account getAccount() {
		return (Account) sessionContent.get(PropertyBean.ACCOUNT);
	}

	/**
	 *	Abfragen ob der User eingeloggt ist 
	 */
	public Boolean getLogin() {
		return login;
	}

	/**
	 *	Setzten des Loggin auf true oder false
	 */
	public void setLogin(Boolean login) {
		this.login = login;
	}

	/**
	 *	Applikations-Root als URI zurueckgeben
	 */
	public URI getRootUri() {
		return rootUri;
	}

	/**
	 *	Root-Verzeicnis als PAth zurueckgeben
	 */
	public Path getRoot() {
		return root;
	}

	/**
	 *	Den aktuellen Datenbankbenutzer zurueckgeben
	 */
	public String getDbUser() {
		return dbUser;
	}

	
	/**
	 *	Den Namen der aktuell verwendete Datenbank holen 
	 */
	public String getDataBase() {
		return dataBase;
	}

	/**
	 *	Eine Message zum Massagestack hinzufuegen
	 */
	/**
	 *	Eine Message zum Massagestack hinzufuegen
	 */
	public void addMesssage(FacesMessage msg) {
		if(getMessageList() == null) {
			messageList = new ArrayList<>();
		}
		this.getMessageList().add(msg);
	}

	/**
	 *	Eine Message aus dem Massagestack entfernen
	 */
	public void removeMesssage(FacesMessage msg) {
		if(getMessageList() != null && getMessageList().contains(msg)) {
			this.getMessageList().remove(msg);
		}
	}

	/**
	 *	Die ganze Liste mit den Messages holen
	 */
	public List<FacesMessage> getMessageList() {
		return messageList;
	}

	/**
	 *	Eine komplete Liste mit den Messages setezen
	 */
	public void setMessageList(List<FacesMessage> msg) {
		this.messageList = msg;
	}

	public String getMailPass() {
		return mailPass;
	}

	public void setMailPass(String mailPass) {
		this.mailPass = mailPass;
	}

	public String getMailUser() {
		return mailUser;
	}

	public void setMailUser(String mailUser) {
		this.mailUser = mailUser;
	}
	
	
}
