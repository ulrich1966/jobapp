package de.juli.jobfxclient.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobfxclient.controller.PropController;

public class PropertyService {
	private static final Logger LOG = LoggerFactory.getLogger(PropertyService.class);
	private static final String PROP_PATH = "/properties.xml";
	
	public static Properties findAppProperties() {
		Properties prop = new Properties();
		try {
			Path path = Paths.get(PropController.class.getResource(PROP_PATH).toURI());
			File file = path.toFile();
			if(file.exists()) {
				prop.loadFromXML(new FileInputStream(path.toFile()));						
			} else {
				new FileNotFoundException(String.format("Datei %s nicht gefunden", path));
			}
		} catch (Exception e) {
			LOG.error("Fehler beim Lesen der der Propertydatei {}", e.getMessage());
		}
		return prop;
	}

	public static void saveAppProperties(Properties prop) {
		try {
			Path path = Paths.get(PropController.class.getResource(PROP_PATH).toURI());
			OutputStream out = new FileOutputStream(path.toFile());
			prop.storeToXML(out, "");
		} catch (Exception e) {
			LOG.error("Fehler beim Schreiben der der Propertydatei {}", e.getMessage());
		}
		
	}
}
