package de.jobapp.jobmodel.test;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Test;

import de.juli.jobapp.jobmodel.service.DocumentService;

public class PathTest {

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		URI uri = DocumentService.class.getResource("/").toURI();
		Path path = Paths.get(uri).resolve("docs");
		System.err.println(path);
		String tmp = System.getProperty("java.io.tmpdir");
		System.err.println(tmp);
		String userHome = System.getProperty("user.home");
		System.err.println(userHome);
		URI uriHome = new URI(System.getProperty("user.home"));
		System.err.println(uriHome);
		Path pathHome = Paths.get(uri);
		System.err.println(pathHome);
	}

}

