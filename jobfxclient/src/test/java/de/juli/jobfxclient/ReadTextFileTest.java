package de.juli.jobfxclient;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.juli.jobfxclient.util.AppProperties;

public class ReadTextFileTest {

	@Test
	public void test() throws Exception {
		Path sourceorig = Paths.get(new URI(AppProperties.getAppdocRootName())).resolve("mail.txt");
		System.out.println(sourceorig);
		Path source = Paths.get("D:/texte/bla.txt");
		Path target = Paths.get("D:/texte");
		try {
			if (Files.exists(source)) {
				List<String> lines = Files.readAllLines(source, StandardCharsets.ISO_8859_1);
				if (lines != null && !lines.isEmpty()) {
					lines.forEach(l -> System.out.println(l));
				} else {
					System.out.println("kein Inhalt");
				}
			} else {
				System.out.println("keine datei");
			}
		} catch (java.nio.charset.MalformedInputException e) {
			e.printStackTrace();
			Assert.assertTrue(false);
		}
	}

}
