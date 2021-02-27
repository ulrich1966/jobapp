package de.juli.jobweb.util;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import de.juli.jobmodel.controller.AccountController;
import de.juli.jobmodel.controller.SourceController;
import de.juli.jobmodel.enums.Title;
import de.juli.jobmodel.model.Account;
import de.juli.jobmodel.model.Company;
import de.juli.jobmodel.model.Contact;
import de.juli.jobmodel.model.Job;
import de.juli.jobmodel.model.Source;
import de.juli.jobmodel.service.PersistService;
import de.juli.jobmodel.util.ToolService;

public class SetUpJobs {
	private PersistService ps = new PersistService();
	private SourceController sc = new SourceController();
	private AccountController ac = new AccountController();
	private List<Source> sources;
	private Account account;
	
	public SetUpJobs(){
		//docs = dc.findByType("odt");
		sources = sc.findAll();
		account = ac.findByName("uli");
	}
	
	public void create() {
		List<Job> jobs = createJobs();
		jobs.forEach(j -> ps.persist(j));	
		jobs.forEach(j -> System.out.println("job: "+j.getId()+" created"));
	}
	
	private List<Job> createJobs() {
		List<Job> jobs = new ArrayList<>();
		Job job = null;
		for (int i = 1; i <= 3; i++) {
			job = new Job();
		
			//Document doc = docs.get(ToolService.randomInt(docs.size()));
			Source source = sources.get(ToolService.randomInt(sources.size()));
			Integer salary = new Integer(ToolService.randomInt(10000, 100000));
		
			job.setNote("note_" + i);
			job.setTitle("title_" + i);
			job.setWebLink("http://google.de");
			job.setSalary(salary);
			job.setJobAdDate(new Date(new java.util.Date().getTime()));

			//job.setLetter(doc);
			job.setSource(source);
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
