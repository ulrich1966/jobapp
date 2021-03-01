package de.juli.jobapp.jobweb.web.app;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

import org.jboss.weld.exceptions.IllegalArgumentException;

import de.juli.jobapp.jobmodel.controller.AccountController;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobweb.util.PropertyBean;


@Named("account")
@RequestScoped
public class AccountBean extends WebBean implements CrudBean {
	private static final long serialVersionUID = 1L;
	private Account model;	
	private int init = 0;
	
	public AccountBean() {
	}
	
	@PostConstruct
	public void init() {
		model = (Account) session.getContent("account");
		try {
			if(model == null) {
				throw new IllegalArgumentException("Kein Model vorhanden");
			}
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
		}
		init++;
		System.out.println(this.getClass().getName() + " init: "+init);
	}

	public Account getModel() {
		return model;
	}

	public void setModel(Account model) {
		this.model = model;
	}

	@Override
	public String create() {
		model = new Account();
		return "";
	}

	@Override
	public String read() {
		return null;
	}

	@Override
	public String update() {
		AccountController controller = new AccountController();
		controller.persist(model);
		return PropertyBean.HOME;	
	}

	@Override
	public String delete() {
		return null;
	}
}
