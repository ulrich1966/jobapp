package de.juli.jobweb.util;

/**
 * Haelt alle fuer die App wichtigen Daten als satische Variabelen 
 *
 */
public class PropertyBean {
	// Die Redirects
	public static final String HOME = "/index.xhtml?faces-redirect=true";
	public static final String APP_HOME = "index.xhtml?faces-redirect=true";
	public static final String JOB = "create_job.xhtml?faces-redirect=true";
	public static final String COMPANY = "create_company.xhtml?faces-redirect=true"; 
	public static final String CONTACT = "create_contact.xhtml?faces-redirect=true";
	public static final String APPLICATION = "create_application.xhtml?faces-redirect=true";
	public static final String DETAILS = "data_view.xhtml?faces-redirect=true";
	public static final String EDIT = "data_edit.xhtml?faces-redirect=true";
	public static final String SOURCE = "job_source.xhtml?faces-redirect=true";
	public static final String UPLOAD = "upload.xhtml?faces-redirect=true";
	public static final String REVIEW = "data_review.xhtml?faces-redirect=true";
	public static final String LIST = "list_view.xhtml?faces-redirect=true";
	public static final String HISTORY = "history.xhtml?faces-redirect=true";
	public static final String SETTINGS = "app_setting.xhtml?faces-redirect=true";
	
	// Session Keys
	public static final String CURRENT_JOB = "currentJob"; 
	public static final String LOGIN_NAME = "loginName";
	public static final String ACCOUNT = "account"; 
}
