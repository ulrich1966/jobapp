package de.juli.jobweb.web;

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

import de.juli.jobmodel.controller.EmController;
import de.juli.jobmodel.model.Account;
import de.juli.jobweb.util.PropertyBean;

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
	
	public Session(){
		init++;
		LOG.debug("\n\tSession constructor: {}", init);
	}
	
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

	public void logOut() {
		login = false;
		sessionContent.remove(PropertyBean.ACCOUNT);
	}
	
	public void addContent(String key, Object value) {
		sessionContent.put(key, value);
	}

	public Object getContent(String key) {
		return sessionContent.get(key);
	}
	
	public String getContentAsString(String key) {
		return getContent(key).toString();
	}

	public Integer getContentAsInt(String key) {
		try {
			return (Integer) getContent(key);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public Boolean getContentAsBool(String key) {
		try {
			return (Boolean) getContent(key);
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return null;
	}

	public void removContent(String key) {
		sessionContent.remove(key);
	}

	public Map<String, Object> getSessionContent() {
		return sessionContent;
	}

	public void setAccount(Account account) {
		sessionContent.put(PropertyBean.ACCOUNT, account);
	}
	
	public Account getAccount() {
		return (Account) sessionContent.get(PropertyBean.ACCOUNT);
	}

	public Boolean getLogin() {
		return login;
	}

	public void setLogin(Boolean login) {
		this.login = login;
	}

	public URI getRootUri() {
		return rootUri;
	}

	public Path getRoot() {
		return root;
	}

	public String getDbUser() {
		return dbUser;
	}

	public String getDataBase() {
		return dataBase;
	}

	public void addMesssage(FacesMessage msg) {
		if(getMessageList() == null) {
			messageList = new ArrayList<>();
		}
		this.getMessageList().add(msg);
	}

	public void removeMesssage(FacesMessage msg) {
		if(getMessageList() != null && getMessageList().contains(msg)) {
			this.getMessageList().remove(msg);
		}
	}

	public List<FacesMessage> getMessageList() {
		return messageList;
	}

	public void setMessageList(List<FacesMessage> msg) {
		this.messageList = msg;
	}
	
	
}
