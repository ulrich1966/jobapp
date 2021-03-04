package de.juli.jobapp.jobweb.web.app;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.AppSetting;
import de.juli.jobapp.jobmodel.service.PersistService;
import de.juli.jobapp.jobweb.web.StyleBean;

@Named("setting")
@RequestScoped
public class AppSettingBean extends WebBean implements CrudBean {
	private static final Logger LOG = LoggerFactory.getLogger(AppSettingBean.class);
	private static final long serialVersionUID = 1L;
	private PersistService service;
	private Boolean newStyle;	
	private AppSetting model;
	private Account account;
	private StyleBean.Styles selectedStyle;
	private StyleBean.Styles [] styles;

	/**
	 * Holt das AppSetting-Objekt aus der Datenbank, Wenn es noch keins gibt, wird 
	 * ein neues in der create - Methode erzeugt.
	 * Fuer das Style-Theme wird das hinterlege Theme des Benutzers geholt und dem
	 * StyleBean.Styles-Objet zugewiesen und das Array der StyleBean.Styles-Objete
	 * fuer die Auswahlbox gefuellt. 
	 */
	@PostConstruct
	public void init() {
		model = getService().getAppSettingController().findFirst();
		if(model == null) {
			this.create();
		}
		account = getSession().getAccount();
		if(account.getStyleTheme() == null) {
			account.setStyleTheme(StyleBean.Styles.DEFAULT.getName());
		}
		setSelectedStyle(account.getStyleTheme());
		styles = StyleBean.Styles.values();
	}

	/**
	 * Setzt fuer den Benutzer das augewaehlte Theme und persistert das Account-Objekt. 
	 * Aktuellisiert mit dem Aufruf von das StyleBean.Styles-Objekt in dieser Bean und ruft die Seite neu auf.
	 * Geht was schief, gib es eine Fehlermeldung. 
	 */
	@Override
	public String update() {
		FacesMessage msg = null;
		try {
			account.setStyleTheme(selectedStyle.getName());
			account = getService().getAccountController().persist(account);
			setSelectedStyle(account.getStyleTheme());
			msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "", "Settings wurden angelegt!");
		} catch (Exception e) {
			LOG.error("{}", e.getMessage());
			msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Das Anlegen der Settings hat nicht geklappt!");
			return "";
		} finally {
			FacesContext.getCurrentInstance().addMessage(null, msg);			
		}
		return "";
	}

	/**
	 * Loescht das aktuelle AppSetting-Objekt aus der Datenbank.
	 */
	@Override
	public String delete() {
		getService().getAppSettingController().remove(model);
		return "";
	}

	@Override
	public String create() {
		this.model = new AppSetting();
		return "";
	}
	
	@Override
	public String read() {
		return null;
	}
	
	private void setSelectedStyle(String styleTheme) {
		selectedStyle = StyleBean.Styles.findByName(account.getStyleTheme());
	}


	// Getter / Setter
	
	public AppSetting getModel() {
		return model;
	}

	public void setModel(AppSetting model) {
		this.model = model;
	}

	public PersistService getService() {
		if(service == null) {
			this.service = new PersistService();
		}
		return service;
	}

	public StyleBean.Styles [] getStyles() {
		return styles;
	}

	public StyleBean.Styles getSelectedStyle() {
		return selectedStyle;
	}

	public void setSelectedStyle(StyleBean.Styles selectedStyle) {
		this.selectedStyle = selectedStyle;
	}

	public Boolean getNewStyle() {
		return newStyle;
	}

	public void setNewStyle(Boolean newStyle) {
		this.newStyle = newStyle;
	}

	public Account getAccount() {
		return account;
	}
	
}
