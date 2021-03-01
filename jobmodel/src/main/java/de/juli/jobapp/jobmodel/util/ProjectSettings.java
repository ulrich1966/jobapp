package de.juli.jobapp.jobmodel.util;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProjectSettings {
	private static String DOC_PATH = "/docs";
	
	public static void setUp() {
	}

	public static Path getDocRoot() {
		try {
			URI uri = ProjectSettings.class.getResource(DOC_PATH).toURI();
			return Paths.get(uri);
		} catch (URISyntaxException e) {
			System.err.println(e.getMessage());
		}
		return null;
	}
}
