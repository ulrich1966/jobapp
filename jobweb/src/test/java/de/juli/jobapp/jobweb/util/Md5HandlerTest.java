package de.juli.jobapp.jobweb.util;

import java.security.NoSuchAlgorithmException;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.util.Md5Handler;

public class Md5HandlerTest {
	private static final Logger LOG = LoggerFactory.getLogger(Md5HandlerTest.class);

	@Test
	public void testGenerateMd5() throws NoSuchAlgorithmException {
		Md5Handler handler = new Md5Handler();
		Assert.assertNotNull(handler);
		String hash = handler.generateMd5("test");
		Assert.assertNotNull(hash);
		LOG.debug(hash);
		LOG.debug("done 1");
	}

	@Test
	public void testCompareMd5() throws NoSuchAlgorithmException {
		Md5Handler handler = new Md5Handler();
		Assert.assertNotNull(handler);
		String hash = handler.generateMd5("test");
		Assert.assertNotNull(hash);
		Assert.assertTrue(handler.compareMd5("test", hash));
		LOG.debug("done 2");
	}

	@Test
	public void testFailCompareMd5() throws NoSuchAlgorithmException {
		Md5Handler handler = new Md5Handler();
		Assert.assertNotNull(handler);
		String hash = handler.generateMd5("bla");
		Assert.assertNotNull(hash);
		Assert.assertFalse(handler.compareMd5(hash, "test"));
		LOG.debug("done 3");
	}

}
