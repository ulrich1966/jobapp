package de.juli.jobapp.jobweb.util;

import java.util.Properties;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.util.AppProperties;
import javassist.NotFoundException;

public class AppPropertiesTest {
	private static final Logger LOG = LoggerFactory.getLogger(AppPropertiesTest.class);
	static int runs = 1;

	@Test
	public void testPropertiesUsersLoadXML() {
		LOG.debug("Die user.xml", AppProperties.USERS_PROP);
		AppProperties properties = AppProperties.getInstance(AppProperties.USERS_PROP);
		Assert.assertNotNull(properties);
		Properties props = properties.propertiesLoadXML();
		Assert.assertNotNull(props);
		props.forEach((key, value) -> LOG.debug("Key : " + key + ", Value : " + value));

		LOG.debug("done {}", runs++);
	}

	@Test
	public void testPropertyCongigFind() throws NotFoundException {
		LOG.debug("Die config.xml {}", AppProperties.DEFAULT_PROP);
		AppProperties properties = AppProperties.getInstance(AppProperties.DEFAULT_PROP);
		Assert.assertNotNull(properties);
		Properties props = properties.propertiesLoadXML();
		Assert.assertNotNull(props);
		props.forEach((key, value) -> LOG.debug("Key : " + key + ", Value : " + value));
		
		LOG.debug("done {}", runs++);
	}

	@Test
	public void testPropertyUserFind() throws NotFoundException {
		AppProperties properties = AppProperties.getInstance(AppProperties.USERS_PROP);
		Assert.assertNotNull(properties);

		String key = "uli.name";
		String result = properties.propertyFind(key);
		Assert.assertNotNull(result);
		LOG.debug("{} {}", key, result);
		
		key = "uli.pass";
		result = properties.propertyFind(key);
		Assert.assertNotNull(result);
		LOG.debug("{} {}", key, result);

		key = "uli.roll";
		result = properties.propertyFind(key);
		Assert.assertNotNull(result);
		LOG.debug("{} {}", key, result);

		LOG.debug("done {}", runs++);
	}

	@Test
	public void testPropertyNotFounfFind() throws NotFoundException {
		AppProperties properties = AppProperties.getInstance(AppProperties.USERS_PROP);
		String key = "uli.password";
		Assert.assertThrows(NotFoundException.class, () -> properties.propertyFind(key));
		
		LOG.debug("done {}", runs++);
	}

}
