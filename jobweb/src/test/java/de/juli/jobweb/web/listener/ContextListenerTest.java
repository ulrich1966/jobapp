package de.juli.jobweb.web.listener;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ContextListenerTest {
	private static final Logger LOG = LoggerFactory.getLogger(ContextListenerTest.class);

	@Test
	public void testContextInitialized() {
		ContextListener listener = new ContextListener();
		Assert.assertNotNull(listener);
		LOG.debug("done 1");
	}

	@Test
	public void testContextDestroyed() {
		ContextListener listener = new ContextListener();
		Assert.assertNotNull(listener);
		listener.contextDestroyed(null);
		LOG.debug("done 2");
	}

}
