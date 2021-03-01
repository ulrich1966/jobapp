package de.juli.jobapp.jobweb.util;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobmodel.model.Account;
import de.juli.jobmodel.util.AccoundHelper;
import javassist.NotFoundException;


public class AccoundHelperTest {
	private static final Logger LOG = LoggerFactory.getLogger(AccoundHelperTest.class);

	@Test
	public void testGetInstance() {
		AccoundHelper helper = AccoundHelper.getInstance();
		Assert.assertNotNull(helper);
		LOG.debug("done 1");
	}

	@Test
	public void testFillAccoundByProperties() throws NotFoundException {
		AccoundHelper helper = AccoundHelper.getInstance();
		Assert.assertNotNull(helper);
		Account account = helper.fillAccoundByProperties("admin");
		Assert.assertNotNull(account);
		account = helper.fillAccoundByProperties("uli");
		LOG.debug("done 2");		
	}
}
