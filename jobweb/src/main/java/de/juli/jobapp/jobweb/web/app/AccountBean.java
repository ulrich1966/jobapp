package de.juli.jobapp.jobweb.web.app;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.jboss.weld.exceptions.IllegalArgumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.controller.ModelController;
import de.juli.jobapp.jobmodel.exeptions.ShitHappendsExeption;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.SenderAddress;
import de.juli.jobapp.jobweb.util.PropertyBean;


@Named("account")
@RequestScoped
public class AccountBean extends WebBean implements CrudBean {
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(AccountBean.class);
	private Account model;	
	
	public AccountBean() {
	}
	
	/**
	 * Das aktuelle Accout-Objekt wird aus der Sessin geholt. 
	 * Wenn noch kein Accout-Objekt angelegt wurde, gib es einen Fehler und 
	 * es wird auf die Startseite umgeleitet. 
	 */
	@PostConstruct
	public void init() {
		model = (Account) session.getContent("account");
		try {
			if(model == null) {
				throw new IllegalArgumentException("Kein Model vorhanden");
			}
		} catch (ShitHappendsExeption e) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "", "Ein aktueller Benutzer wurde nicht gefunden!");
			FacesContext.getCurrentInstance().addMessage("frm:user", msg);
			LOG.error("{}", e.getMessage());
			try {
				FacesContext.getCurrentInstance().getExternalContext().redirect(String.format("app/%s", PropertyBean.HOME));
			} catch (IOException e1) {
				LOG.error("{}", e1.getMessage());
			}
		}
	}

	public Account getModel() {
		return model;
	}

	public void setModel(Account model) {
		this.model = model;
	}

	@Override
	public String create() {
		if (model.getAddress() == null ) {
			model.setAddress(new SenderAddress());
		}
		update();
		return "";
	}

	@Override
	public String read() {
		return null;
	}

	@Override
	public String update() {
		ModelController controller = super.getController();
		controller.persist(model);
		return PropertyBean.DETAILS;	
	}

	@Override
	public String delete() {
		return null;
	}
	
}
