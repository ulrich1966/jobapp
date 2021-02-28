package de.juli.jobweb.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.NotFoundException;

public class AppProperties {
	private static final Logger LOG = LoggerFactory.getLogger(AppProperties.class);
	public static final String DEFAULT_PROP = "properties/config.xml";
	public static final String USERS_PROP = "properties/users.xml";
	private Properties currentProps = null;
	private String propertyFile = null;

	private AppProperties(String propFile) {
		currentProps = new Properties();
		propertyFile = propFile;
	}

	public static AppProperties getInstance(String propFile) {
		return new AppProperties(propFile);
	}
	
	public Properties propertiesLoadXML() {
		try (InputStream input = AppProperties.class.getClassLoader().getResourceAsStream(propertyFile)) {
			currentProps.loadFromXML(input);
			return currentProps;
		} catch (IOException e) {
			LOG.error("{}", e);
		}
		return null;
	}

	public String propertyFind(String prop) throws NotFoundException {
		propertiesLoadXML();
		String result = currentProps.getProperty(prop);
		if (result == null) {
			String msg = String.format("Es wurde kein passender Eintrag für %s gefunden!", prop);
			LOG.error(msg);
			throw new NotFoundException(msg);
		}
		return result;
	}
}
