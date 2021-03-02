package de.jobapp.jobmodel.test;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.util.AccountHelper;
import javassist.NotFoundException;


public class AccountHelperTest {
	private static final Logger LOG = LoggerFactory.getLogger(AccountHelperTest.class);

	@Test
	public void testGetInstance() {
		AccountHelper helper = AccountHelper.getInstance();
		Assert.assertNotNull(helper);
		LOG.debug("done 1");
	}

	@Test
	public void testFillAccoundByProperties() throws NotFoundException {
		AccountHelper helper = AccountHelper.getInstance();
		Assert.assertNotNull(helper);
		Account account = helper.fillAccoundByProperties("admin");
		Assert.assertNotNull(account);
		account = helper.fillAccoundByProperties("uli");
		LOG.debug("done 2");		
	}
}
