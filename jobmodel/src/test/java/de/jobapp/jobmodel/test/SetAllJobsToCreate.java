package de.jobapp.jobmodel.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.juli.jobapp.jobmodel.controller.AccountController;
import de.juli.jobapp.jobmodel.enums.AppHistory;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.History;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.service.PersistService;

public class SetAllJobsToCreate {

	@Test
	public void test() {
		PersistService service = new PersistService();
		Assert.assertNotNull(service);
		AccountController contoller = new AccountController();
		Assert.assertNotNull(contoller);

		Account account = contoller.findByName("uli");
		Assert.assertNotNull(account);
		List<Job> jobs = account.getJobs();
		Assert.assertNotNull(jobs);
		Assert.assertTrue(jobs.size() > 0);
		
		//jobs.forEach(e -> e.addJobHistory(new JobHistory(JobState.READY_TO_PERSIST)));
		//jobs.forEach(e -> e.addJobHistory(new JobHistory(JobState.CREATED)));
		//jobs.forEach(e -> e.addJobHistory(new JobHistory(JobState.DOC_CREATED)));
		jobs.forEach(e -> e.addHistory(new History(AppHistory.NOT_SEND)));
		jobs.forEach(e -> service.persist(e));
		
		
	}

}
