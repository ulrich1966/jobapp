package de.juli.jobapp.jobweb.service;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.model.Job;

public class MailerService {
	private static final Logger LOG = LoggerFactory.getLogger(MailerService.class);

	/**
	 * Generiert einen E-Mail-Text aus dem hinterlegten Template, das im Job-Objekt hinterlegt ist und ruft dann 
	 * das default E-Mailprogramm des Systems auf, in die der Text als Body gesetztn wird. Die E-Mailadresse und 
	 * das Betreff werden aus dem Job-Objekt ueber den Firmenkontakt gesetzt. Ist das ganze Felerfrei abgelaufen, 
	 * wird success = true zurueckgegeben.   
	 */
	public boolean createDefaultMailerMail(Job model) {
		boolean success = false;
		
		try {
			String email = model.getCompany().getContact().getEmail().trim();
			String title = model.getTitle().replace(" ", "%20").trim();
			String body = createMailBody(model.getEmail().getTarget());

			Desktop desktop = Desktop.getDesktop();
			String message = String.format("mailto:%s?subject=%s&body=%s", email, title, body);
			URI uri = URI.create(message);
			desktop.mail(uri);
			success = true;
		} catch (IOException e) {
			success = false;
			LOG.error("{}", e.getMessage());
		}

		return success;
	}

	/**
	 * Holt die ausgefuellte E-Mail Datei aus dem hinterlegten Pfad und generiert den Text fuer den E-Mail Body,
	 * der als String zurueckgegeben wird. 
	 */
	private String createMailBody(String template) throws IOException {
		Path source = Paths.get(template);
		List<String> lines = Files.readAllLines(source, StandardCharsets.UTF_8);
		final StringBuilder sb = new StringBuilder();
		lines.forEach(l -> sb.append(l.replace(" ", "%20")+"%0A%0D"));

		return sb.toString();
	}
}
