package de.juli.jobapp.jobmodel.util;

import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.model.Account;
import javassist.NotFoundException;


public class AccountHelperTest {
	private static final Logger LOG = LoggerFactory.getLogger(AccountHelperTest.class);
	
	@Test
	public void testStart() throws Exception {
		//testGetInstance();
		//testFillAccoundByProperties();
		testFillAccoundByJson();
	}
	

	@Ignore
	@Test
	public void testGetInstance() {
		AccountHelper helper = AccountHelper.getInstance();
		Assert.assertNotNull(helper);
		LOG.debug("done 1");
	}

	@Ignore
	@Test
	public void testFillAccoundByProperties() throws NotFoundException {
		AccountHelper helper = AccountHelper.getInstance();
		Assert.assertNotNull(helper);
		Account account = helper.fillAccoundByProperties("admin");
		Assert.assertNotNull(account);
		account = helper.fillAccoundByProperties("uli");
		LOG.debug("done 2");		
	}

	@Ignore
	@Test
	public void testFillAccoundByJson() throws NotFoundException {
		AccountHelper helper = AccountHelper.getInstance();
		Assert.assertNotNull(helper);
		List<Account> list = helper.fillAccoundByJsonFile("userList");
		Assert.assertNotNull(list);
		Assert.assertFalse(list.isEmpty());
		list.forEach(e -> LOG.debug("{}", e));

		LOG.debug("done 2");		
	}
}
