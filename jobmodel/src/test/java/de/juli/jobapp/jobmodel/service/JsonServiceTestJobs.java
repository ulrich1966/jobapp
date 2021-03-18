package de.juli.jobapp.jobmodel.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.star.lang.NullPointerException;

import de.juli.jobapp.jobmodel.enums.AppHistory;
import de.juli.jobapp.jobmodel.enums.JobState;
import de.juli.jobapp.jobmodel.enums.Title;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.Company;
import de.juli.jobapp.jobmodel.model.Contact;
import de.juli.jobapp.jobmodel.model.Email;
import de.juli.jobapp.jobmodel.model.History;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.model.Source;
import de.juli.jobapp.jobmodel.model.State;
import javassist.NotFoundException;

public class JsonServiceTestJobs {
	private static final Logger LOG = LoggerFactory.getLogger(JsonServiceTestJobs.class);

	@Test
	public void test() throws Exception {
		testWriteJob();
		testWrite_List();
		testRead_List();
	}

	@Ignore
	@Test
	public void testWriteJob() throws NotFoundException, NullPointerException {
		List<Job> jobs = new ArrayList<>();
		for(int i=0; i < 3; i++) {
			jobs.add(createJob(i));
		}

		JsonService service = new JsonService();
		Assert.assertNotNull(service);
		String string = service.write(jobs, "job-list");
		Assert.assertNotNull(string);
		
		
		LOG.debug("{}", string);
		LOG.debug("done");
	}
	
	

	private Job createJob(int i) throws NotFoundException {
		Job job = new Job();
		job = new Job();
		job.setTitle("Toller Job " + i);
		job.setJobfunction("Joberfüller für Job " + i);
		job.setNote("Der beste Job, den man kriegen kann" + i);
		job.setWebLink("https://www.qwant.com");
		job.setEmail(createEmail());
		job.setSalary(1);
		job.setJobAdDate(new Date(new java.util.Date().getTime()));
		Company company = createCompany(i);
		job.setCompany(company);
		// !!! Kreisabhaenigkeit der Job muss aus der Company
		company.removeJob(job);
		job.setSource(createSource(i));
		job.addState(createState(i));
		job.addHistory(createHistory(i));
		
		return job;
	}

	private Email createEmail() {
		return null;
	}

	private History createHistory(int i) {
		History history = new History();
		history.setAppHistory(AppHistory.NOT_SEND);
		history.setNote("Ein frisch erzeugter History-Eintrag");
		history.setTimeStamp(new java.util.Date().getTime());
		return history;
	}

	private State createState(int i) {
		State sate = new State();
		sate.setJobState(JobState.READY_TO_PERSIST);
		sate.setNote("Dieser Job ist neu angelegt");
		sate.setTimeStamp(new java.util.Date().getTime());
		return sate;
	}

	private Source createSource(int i) {
		Source source = new Source();
		source.setName("Job Anzeigenbörse " + i);
		source.setPronomen("auf der");
		return source;
	}
	
	private Account readAccount(String name) throws NotFoundException {
		JsonService service = new JsonService();
		Assert.assertNotNull(service);
		Account account = (Account) service.read(Account.class, name);
		Assert.assertNotNull(account);
		return account;
	}
	
	private Company createCompany(int i) {
		Company model = new Company();
		model.setCity("Stadt der Firma " + i);
		model.setName("Unternehen des Jobs " + i);
		model.setStreet("Straße der Firma" + i);
		model.setWeb("https://www.qwant.com");
		model.setZip("123"+i);
		model.setContact(createContact(i));
		return model;

	}
	
	private Contact createContact(int i) {
		Contact model = new Contact();
		model.setTitle(Title.HERR);
		model.setEmail("uli.test@fantasymail.de");
		model.setFirstName("Vorname " + i);
		model.setLastName("Nachname " + i);
		model.setMobile("123 000" + i);
		model.setPhone("123 000" + i);
		return model;
	}

	@Ignore
	@Test
	public void testWrite_List() throws Exception {
		Job job1 = new Job();
		Job job2 = new Job();

		JsonService service = new JsonService();
		Assert.assertNotNull(service);

		List<Job> list = new ArrayList<>();
		list.add(job1);
		list.add(job2);

		String result = service.write(list, "jobList");
		Assert.assertNotNull(result);
		Assert.assertFalse(result.isEmpty());
		LOG.debug(result);
	}

	@Ignore
	@Test
	public void testRead_List() throws Exception {
	
	}

}
