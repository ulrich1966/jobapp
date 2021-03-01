package de.juli.jobapp.jobweb.util;

import java.nio.file.Path;
import java.nio.file.Paths;

public class AppDirectories {
	public static final String DOC_DIR = "docs";
	public static final String UPLOAD_DIR = "uploads";
	public static final String EMAIL_DIR = "email";
	public static final String LETTER_DIR = "letter";
	public static final String VITA_DIR = "vita";
	public static final String APPLIES_DIR = "applies";
	
	public static final Path DOC_SOURCE_PATH = Paths.get("/docs");
	
	public static void setUp() {
	}

	public static Path getLetterPath(Path root, Path user) {
		return docRoot(root).resolve(user).resolve(LETTER_DIR);
	}

	public static Path getVitaPath(Path root, Path user) {
		return docRoot(root).resolve(user).resolve(VITA_DIR);
	}
	
	public static Path getEmailPath(Path root, Path user) {
		return docRoot(root).resolve(user).resolve(EMAIL_DIR);
	}

	public static Path getTargetPath(String root, String user) {
		return getTargetPath(Paths.get(root), Paths.get(user));
	}
	
	public static Path getTargetPath(Path root, Path user) {
		return docRoot(root).resolve(user).resolve(APPLIES_DIR);
	}

	public static String getTargetPathAsString(Path root, String name) {
		return getTargetPath(root, Paths.get(name)).toString();
	}
	
	private static Path docRoot(Path root) {
		Path serverRoot = root.getParent().getParent().getParent().getParent();
		Path uploads = serverRoot.resolve(AppDirectories.UPLOAD_DIR);
		Path docRoot = uploads.resolve(AppDirectories.DOC_DIR);
		return docRoot;
	}

}
