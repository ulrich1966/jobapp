package de.juli.jobweb.web;

import java.io.IOException;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobweb.util.PropertyBean;

@Named("style")
@RequestScoped
public class StyleBean implements Serializable {
	private static final Logger LOG = LoggerFactory.getLogger(StyleBean.class);
	private static final long serialVersionUID = 1L;
	private String currentStyle;
	private String name = "dark";
	@Inject
	private Session session;
	

	/**
	 * Es wird auf Vorhandensein der Session geprüft und ggf die Seite umgeleitet. 
	 * Dabei Zunaechst wird versucht auf die Startseite zu gelang. Wenn das missgluegt 
	 * wird eine 500 Server-Error erzeugt.
	 * Ging alles gut, wird der Stylename fuer das Theme zugewiesen, wenn der null ist 
	 * wird das default Theme genommen und ein Hinweis generiert; 
	 */
	@PostConstruct
	public void init() {
		if(session == null) {
			try {
				throw new IllegalStateException("Es wrude keine Session gefunden");				
			} catch(IllegalStateException e) {
				LOG.error("{}", e.getMessage());
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Es ist kein Benutzer eingeloggt");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				try {
					FacesContext.getCurrentInstance().getExternalContext().redirect(PropertyBean.HOME);
				} catch (IOException e1) {
					LOG.error("{}", e1.getMessage());
					FacesContext.getCurrentInstance().getExternalContext().setResponseStatus(500);
				}
			}
		}		

		//TODO Zeitverschwendung mach dal lieber ueber unterschiedliche css Dateien
//		if(session.getAccount() == null) {
//			name = "dark";
//			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Du hast noch kein Theme ausgewaehlt. Das kann bei den App Settings erfolgen");
//			session.addMesssage(msg);
//		}
	}
	
	public StyleBean() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public String getCurrentStyle() {
		return currentStyle;
	}

	/**
	 * Damit der Benutzer unter den zur Verfuegung stehenden Themes auswaelen kann
	 * wird diese Styles Enum bereitgestellt.
	 */
	//TODO Das sollte in deiner Konfigurationsdatei stattfinden
	public static enum Styles {
		DEFAULT("default"),
		DARK("dark"),
		LIGHT("light");
		
		private String name;
		
		public static Styles findByName(String value){
			for (Styles e : Styles.values()) {
				if(e.getName().equalsIgnoreCase(value)) {
					return e;
				}
			}
			return null;
		}
	
		Styles(String name){
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
}
	
