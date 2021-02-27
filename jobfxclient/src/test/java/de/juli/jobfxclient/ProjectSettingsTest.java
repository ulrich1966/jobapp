package de.juli.jobfxclient;

import java.nio.file.Path;

import org.junit.Test;

public class ProjectSettingsTest {

	@Test
	public void testSetUp() throws Exception {
		Path path = ProjectSettings.getDocRoot();
		System.out.println(""+path);
	}
}
