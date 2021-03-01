package de.juli.jobapp.jobweb.viewmodel;

import java.sql.Date;
import java.time.LocalDate;

import de.juli.jobmodel.enums.Title;
import de.juli.jobmodel.model.Company;
import de.juli.jobmodel.model.Contact;
import de.juli.jobmodel.model.Email;
import de.juli.jobmodel.model.History;
import de.juli.jobmodel.model.Job;
import de.juli.jobmodel.model.Letter;
import de.juli.jobmodel.model.Source;
import de.juli.jobmodel.model.State;
import de.juli.jobmodel.model.Vita;

/**
 * Vereifacht den Zugriff auf das Model in den View und 
 * uebernimmt Konvertierunrsaufgaben als Aufbereitung fuer die Anzeige   
 */
public class CurrentData {
	private static final String SELECT_TXT = "(select ...)";
	private Job job;
	private Company company;
	private Contact contact;
	private Title title;
	private Source source;
	private State state;
	private History history;
	// // Selections
	// private Title slectedTitle;
	// private Document slectedDocument;
	// private JobAdSource slectedJobAdSource;

	public CurrentData() {
		super();
		this.job = new Job();
		this.company = new Company();
		this.contact = new Contact();
		this.title = null;
		this.source = new Source();
	}

	public CurrentData(Job job) {
		this.job = job;
		this.company = this.job.getCompany();
		if (this.company.getContact() != null) {
			this.contact = this.company.getContact();
			this.title = this.contact.getTitle();
		} else {
			this.contact = new Contact();
		}
		this.source = this.job.getSource();
		this.state = this.job.getStateLastEntry();
		this.history = this.job.getHistoryLastEntry();
	}

	public Job getJob() {
		return job;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Contact getContact() {
		return contact;
	}

	public void setContact(Contact contact) {
		this.contact = contact;
	}

	public Title getTitle() {
		return title;
	}

	public void setTitle(Title title) {
		this.title = title;
	}

	public Source getSource() {
		return source;
	}

	public void setSource(Source source) {
		this.source = source;
	}
	
	public State getState() {
		return state;
	}

	public History getHistory() {
		return history;
	}
	
	// Convert
	public java.util.Date getDateConvert() {
		if (job.getJobAdDate() == null) {
			job.setJobAdDate(Date.valueOf(LocalDate.now()));
		}
		return new java.util.Date(job.getJobAdDate().getTime());
	}

	public void setDateConvert(java.util.Date dateConvert) {
		if (dateConvert != null) {
			job.setJobAdDate(new Date(dateConvert.getTime()));
		} else {
			job.setJobAdDate(null);
		}
	}


	public CurrentData update() {
		job.setCompany(company);
		if (contact != null) {
			contact.setTitle(title);
			company.setContact(contact);
		}
		if (job.getVita() == null) {
			job.setVita(new Vita(SELECT_TXT));
		} else {
			if (SELECT_TXT.equals(job.getVita().getName())) {
				job.setVita(null);
			}
		}
		if (job.getLetter() == null) {
			job.setLetter(new Letter(SELECT_TXT));
		} else {
			if (SELECT_TXT.equals(job.getLetter().getName())) {
				job.setLetter(null);
			}
		}
		if (job.getEmail() == null) {
			job.setEmail(new Email(SELECT_TXT));
		} else {
			if (job.getEmail() != null && SELECT_TXT.equals(job.getEmail().getName())) {
				job.setEmail(null);
			}
		}
		job.setSource(source);
		return this;
	}
}
