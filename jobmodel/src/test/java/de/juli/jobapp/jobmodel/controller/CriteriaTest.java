package de.juli.jobapp.jobmodel.controller;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.model.Account;

public class CriteriaTest {
	private static final Logger LOG = LoggerFactory.getLogger(CriteriaTest.class);

	@Test
	public void testGetTableSize() {
		CriteriaController<Account> cb = new CriteriaController<>();
		Assert.assertNotNull(cb);
		Long size = cb.getTableSize(Account.class);
		Assert.assertNotNull(size);
		LOG.debug("{}", size);
	}

}
