package de.juli.jobapp.jobweb;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Properties;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PropertyTest {
	private static final Logger LOG = LoggerFactory.getLogger(PropertyTest.class);
	private static URL resourceUrl = null;

	@Test
	public void test() throws Exception {
		resourceUrl = this.getClass().getResource("/properties/config.prop");
		// createProp();
		//readProp();
		//loadFromClassPath();
		loadXML();
	}

	public void readProp() {
		LOG.debug("{}", resourceUrl.toString());
		try (InputStream input = new FileInputStream(resourceUrl.getFile())) {
			Properties prop = new Properties();

			// load a properties file
			prop.load(input);

			// get the property value and print it out
			LOG.debug("{}", prop.getProperty("db.url"));
			LOG.debug("{}", prop.getProperty("db.user"));
			LOG.debug("{}", prop.getProperty("db.password"));
			LOG.debug("{}", prop.getProperty("test"));

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

	public void createProp() {
		LOG.debug("{}", resourceUrl.toString());
		try (OutputStream output = new FileOutputStream(resourceUrl.getFile())) {
			Properties prop = new Properties();
			prop.setProperty("db.url", "localhost");
			prop.setProperty("db.user", "mkyong");
			prop.setProperty("db.password", "password");

			// save properties to project root folder prop.store(output, null);
			prop.store(output, null);
			LOG.debug("{}", prop);

		} catch (IOException io) {
			io.printStackTrace();
		}
	}

	public void loadFromClassPath() {
		try (InputStream input = Test.class.getClassLoader().getResourceAsStream("properties/config.prop")) {
			Properties prop = new Properties();
			if (input == null) {
				throw new NullPointerException("input ist null");
			}
			prop.load(input);
			prop.forEach((key, value) -> LOG.debug("Key : " + key + ", Value : " + value));

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	public void loadXML() throws Exception {
		Properties props = new Properties();

		try (InputStream input = Test.class.getClassLoader().getResourceAsStream("properties/config.xml")) {
			props.loadFromXML(input);
			props.forEach((key, value) -> LOG.debug("Key : " + key + ", Value : " + value));
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}