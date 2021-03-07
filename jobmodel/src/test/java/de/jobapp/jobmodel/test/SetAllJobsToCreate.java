package de.jobapp.jobmodel.test;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.juli.jobapp.jobmodel.controller.ModelController;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.Job;

public class SetAllJobsToCreate {

	@Test
	public void test() {
		ModelController controller = new ModelController();
		Assert.assertNotNull(controller);
		Account account = controller.findByName(Account.class, "uli");
		Assert.assertNotNull(account);
		List<Job> jobs = account.getJobs();
		Assert.assertNotNull(jobs);
		Assert.assertTrue(jobs.size() > 0);
	}
}
