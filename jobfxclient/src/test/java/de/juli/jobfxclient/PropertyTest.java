package de.juli.jobfxclient;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.junit.After;
import org.junit.Test;

public class PropertyTest {
	private static Writer writer = null;
	private static Reader reader = null;


	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws IOException, URISyntaxException {
		//System.getProperties().loadFromXML(PropertyTest.class.getResourceAsStream("/properties.xml"));
		Properties prop = new Properties();
		Path path = Paths.get(PropertyTest.class.getResource("/properties.xml").toURI());
		prop.loadFromXML(new FileInputStream(path.toFile()));
		prop.list(System.out);
		String property = prop.getProperty("email");
		System.out.println(property);
//		prop.put("email", "ukrich.kloodt@gmx.de");
//	    
//		OutputStream out = new FileOutputStream(path.toFile());
//		prop.storeToXML(out, "bla");

	}

}
