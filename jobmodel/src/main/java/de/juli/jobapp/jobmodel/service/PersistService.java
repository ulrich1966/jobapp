package de.juli.jobapp.jobmodel.service;

import java.io.Serializable;

import de.juli.jobapp.jobmodel.controller.AccountController;
import de.juli.jobapp.jobmodel.controller.AppSettingController;
import de.juli.jobapp.jobmodel.controller.CompanyController;
import de.juli.jobapp.jobmodel.controller.ContactController;
import de.juli.jobapp.jobmodel.controller.DocumentController;
import de.juli.jobapp.jobmodel.controller.JobController;
import de.juli.jobapp.jobmodel.controller.SourceController;
import de.juli.jobapp.jobmodel.model.Job;

public class PersistService implements Serializable {
	private static final long serialVersionUID = 1L;
	private AccountController accountController;
	private CompanyController companyController;
	private ContactController contactController;
	private DocumentController documentController;
	private JobController jobController;
	private SourceController sourceController;
	private AppSettingController appSettingController;
	
	public Job persist(Job model) {
		getJobController().persist(model);
		return model;
	}

//	public Job persistJobDoc(Job model){
//		getDocumentController().persist(model.getLetter());
//		getJobController().persist(model);
//		return model;
//	}
	
	public AccountController getAccountController() {
		if(accountController == null) {
			accountController = new AccountController();			
		}
		return accountController;
	}
	public CompanyController getCompanyController() {
		if(companyController == null) {
			companyController = new CompanyController();
		}
		return companyController;
	}
	public DocumentController getDocumentController() {
		if(documentController == null) {
			documentController = new DocumentController();
		}
		return documentController;
	}
	public JobController getJobController() {
		if(jobController == null) {
			jobController = new JobController();
		}
		return jobController;
	}
	public SourceController getSourceController() {
		if(sourceController == null) {
			sourceController = new SourceController();
		}
		return sourceController;
	}

	public ContactController getContactController() {
		if(contactController == null) {
			contactController = new ContactController();
		}
		return contactController;
	}

	public AppSettingController getAppSettingController() {
		if(appSettingController == null) {
			appSettingController = new AppSettingController();
		}
		return appSettingController;
	}
}
