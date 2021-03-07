package de.juli.jobapp.jobweb.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import de.juli.jobapp.jobmodel.model.Email;
import de.juli.jobapp.jobmodel.model.Letter;
import de.juli.jobapp.jobmodel.model.Vita;

/**
 * Verwaltung der Folder Pathes fur die Bewerbungsunterlagen 
 */
public class DirService {
	
	/**
	 * Liest fuer den aktuellen Benutzer die hinterlegten Lebenslaeuf Tempates aus dem Verzeichnis des Servers 
	 */
	public List<Vita> vitaDir(Path path) throws IOException {
		List<Vita> docs = new ArrayList<>(); 
		List<Path> list = Files.walk(path).filter(p -> p.toFile().isFile()).collect(Collectors.toList());
		for (Path source : list) {
			String fileName = source.getFileName().toString();
			docs.add(new Vita(source.toString(), fileName));
		}
		return docs;
	}

	/**
	 * Liest fuer den aktuellen Benutzer die hinterlegten Anschreiben Tempates aus dem Verzeichnis des Servers 
	 */
	public List<Letter> letterDir(Path path) throws IOException {
		List<Letter> docs = new ArrayList<>(); 
		List<Path> list = Files.walk(path).filter(p -> p.toFile().isFile()).collect(Collectors.toList());
		for (Path source : list) {
			String fileName = source.getFileName().toString();
			docs.add(new Letter(source.toString(), fileName));
		}
		return docs;
	}

	/**
	 * Liest fuer den aktuellen Benutzer die hinterlegten E-Mail Tempates aus dem Verzeichnis des Servers 
	 */
	public List<Email> emailDir(Path path) throws IOException {
		List<Email> docs = new ArrayList<>(); 
		List<Path> list = Files.walk(path).filter(p -> p.toFile().isFile()).collect(Collectors.toList());
		for (Path source : list) {
			String fileName = source.getFileName().toString();
			docs.add(new Email(source.toString(), fileName));
		}
		return docs;
	}
}
