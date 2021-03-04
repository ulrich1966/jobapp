package de.juli.jobapp.jobweb.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.controller.CriteriaController;
import de.juli.jobapp.jobmodel.enums.Title;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.Company;
import de.juli.jobapp.jobmodel.model.Contact;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.service.PersistService;
import de.juli.jobapp.jobmodel.util.AccountHelper;
import de.juli.jobapp.jobmodel.util.AppProperties;
import de.juli.jobapp.jobmodel.util.ToolService;
import de.juli.jobapp.jobweb.exeptions.ShittHappensExeption;

public class SetUpDataBase {
	private static final Logger LOG = LoggerFactory.getLogger(SetUpDataBase.class);
	private PersistService ps = new PersistService();
	private Account account;
	private static SetUpDataBase setUpDataBase;

	private SetUpDataBase() {
	}

	public static SetUpDataBase getInstance() {
		if (setUpDataBase == null) {
			setUpDataBase = new SetUpDataBase();
		}
		return setUpDataBase;
	}

	/**
	 * Findest duch den CriteriaController heraus, ob eine Tabelle eines bestimmten durch Class<T> clazz
	 * angegebenen Typs Daten saetze enthaelt und gibt ensprechend true/false zurueck.   
	 */
	public <T> boolean tableIsEmty(Class<T> clazz) {
		CriteriaController<T> cc = new CriteriaController<>();
		Long size = cc.getTableSize(clazz);
		if (size != null) {
			LOG.debug("Datacount: {}", size);
		} else {
			throw new ShittHappensExeption(String.format("Das Ersestellen eines Accounds fuer %s ist voll in die Hose gegangen\n" + "Die Anzahl der Datensaetze in der Tabelle %s konnte nicht ermittelt werden", clazz.getSimpleName()));
		}
		if(size == 0) {
			return true;
		}
		return false;
	}

	public boolean persitSomeAccounds() {
		try {
			String value = AppProperties.getInstance(AppProperties.CONFIG_PROP).propertyFind("json.data.user");
			List<Account> accounds = AccountHelper.getInstance().fillAccoundByJsonFile(value);
			accounds.forEach(e -> ps.getAccountController().persist(e));
			return true;
		} catch (Exception e) {
			LOG.error("{}", e.getMessage());
		}
		return false;

	}

//	public <T> void create(Model model) {
//		List<Job> jobs = createJobs();
//		jobs.forEach(j -> 
//		jobs.forEach(j -> LOG.debug("\"job ID: {} created", j.getId()));
//	}

	private List<Job> createJobs() {
		List<Job> jobs = new ArrayList<>();
		Job job = null;
		for (int i = 1; i <= 3; i++) {
			job = new Job();

			// Document doc = docs.get(ToolService.randomInt(docs.size()));
			// Source source =
			// sources.get(ToolService.randomInt(sources.size()));
			Integer salary = new Integer(ToolService.randomInt(10000, 100000));

			job.setNote("note_" + i);
			job.setTitle("title_" + i);
			job.setWebLink("http://google.de");
			job.setSalary(salary);
			job.setJobAdDate(new Date(new java.util.Date().getTime()));

			// job.setLetter(doc);
			// job.setSource(source);
			job.setCompany(createCompany(i));

			account.addJob(job);

			jobs.add(job);
		}
		return jobs;
	}

	private Company createCompany(int i) {
		Company model = new Company();
		model.setCity("city_" + i);
		model.setName("name_" + i);
		model.setStreet("street_" + i);
		model.setWeb("http://google.de");
		model.setZip("zip_" + i);
		model.setContact(createContact(i));
		return model;

	}

	private Contact createContact(int i) {
		Contact model = null;
		model = new Contact();
		model.setTitle(Title.values()[ToolService.randomInt(Title.values().length - 1)]);
		model.setEmail("uli.test@fantasymail.de");
		model.setFirstName("firstName_" + i);
		model.setLastName("lastName_" + i);
		model.setMobile("123 000" + i);
		model.setPhone("123 000" + i);
		return model;
	}
}
