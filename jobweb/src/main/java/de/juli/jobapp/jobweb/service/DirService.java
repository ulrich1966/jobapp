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

public class DirService {
	
//	public Document[] dirContentToArray(Path path) throws IOException {
//		List<Document> list = dirContent(path);
//		return list.toArray(new Document[list.size()]);
//	}
	
//	public List<Document> dirContent(Path path) throws IOException {
//		List<Document> docs = new ArrayList<>(); 
//		List<Path> list = Files.walk(path).filter(p -> p.toFile().isFile()).collect(Collectors.toList());
//		for (Path source : list) {
//			String fileName = source.getFileName().toString();
//			docs.add(new Document(source.toString(), fileName));
//		}
//		return docs;
//	}

	public List<Vita> vitaDir(Path path) throws IOException {
		List<Vita> docs = new ArrayList<>(); 
		List<Path> list = Files.walk(path).filter(p -> p.toFile().isFile()).collect(Collectors.toList());
		for (Path source : list) {
			String fileName = source.getFileName().toString();
			docs.add(new Vita(source.toString(), fileName));
		}
		return docs;
	}

	public List<Letter> letterDir(Path path) throws IOException {
		List<Letter> docs = new ArrayList<>(); 
		List<Path> list = Files.walk(path).filter(p -> p.toFile().isFile()).collect(Collectors.toList());
		for (Path source : list) {
			String fileName = source.getFileName().toString();
			docs.add(new Letter(source.toString(), fileName));
		}
		return docs;
	}

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
