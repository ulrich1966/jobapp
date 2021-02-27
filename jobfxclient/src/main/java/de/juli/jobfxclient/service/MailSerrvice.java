package de.juli.jobfxclient.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MailSerrvice {
	private static final Logger LOG = LoggerFactory.getLogger(MailSerrvice.class);

	private String smtp = "mail.gmx.net";
	// private int port = 587;
	private int port = 465;
	private String user = "ulrich.kloodt@gmx.de";
	private String pass = "Wrssdnuw@1966";
	private String[] recipients = { "ulrich.kloodt@gmx.de" };
	private MultiPartEmail email = null;

	public String sendSimpleMail() throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(smtp);
		email.setSmtpPort(port);
		email.setSSL(true);
		email.setAuthenticator(new DefaultAuthenticator(user, pass));
		email.setFrom(recipients[0]);
		email.setSubject("TestMail");
		email.setMsg("This is a test mail ... :-)");
		email.addTo(recipients[0]);
		email.send();
		return null;
	}

	public boolean sendAttatchmentMail(Map<String, Path> attatchs, String applyAlias, String profilAlias) throws EmailException, IOException {
		EmailAttachment apply = null;
		EmailAttachment profil = null;
		Path path = null;
		StringBuilder sb = new StringBuilder();

		path = attatchs.get("apply");
		if (path != null) {
			boolean exists = path.toFile().exists();
			if (exists) {
				apply = new EmailAttachment();
				apply.setPath(path.toString());
				apply.setDisposition(EmailAttachment.ATTACHMENT);
				apply.setDescription("");
				apply.setName(applyAlias);
				sb.append(path.toString() + "\n");
			} else {
				LOG.error("Anschreiben konnte nicht erzeugt werden. Datei {}  nicht gefunden", attatchs.get("apply"));
				return false;
			}
		}

		path = attatchs.get("profil");
		if (path != null) {
			boolean exists = path.toFile().exists();
			if (exists) {
				profil = new EmailAttachment();
				profil.setPath(path.toString());
				profil.setDisposition(EmailAttachment.INLINE);
				profil.setDescription("Profil");
				profil.setName(profilAlias);
				sb.append(path.toString() + "\n");
			} else {
				LOG.error("Anschreiben konnte nicht erzeugt werden. Datei {}  nicht gefunden", attatchs.get("apply"));
				return false;
			}
		}

		path = attatchs.get("email");
		if (path != null) {
			boolean exists = path.toFile().exists();
			if (exists) {
				List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
				StringBuilder msg = new StringBuilder();
				lines.forEach(line -> msg.append(line + "\n"));

				email = new MultiPartEmail();
				email.setHostName(smtp);
				email.setSmtpPort(port);
				email.setSSL(true);
				email.setAuthenticator(new DefaultAuthenticator(user, pass));

				email.addTo(recipients[0]);
				email.setFrom(recipients[0]);
				email.setSubject("The Attatchment");
				email.setMsg(msg.toString());
				sb.append(path.toString() + "\n");
			} else {
				LOG.error("Anschreiben konnte nicht erzeugt werden. Datei {}  nicht gefunden", attatchs.get("apply"));
				return false;
			}
		}
		
		if (email != null) {
			System.out.println(sb.toString());
			email.attach(apply);
			email.attach(profil);
			String send = email.send();			
			LOG.info("Send Email {}", send);
		}
		return true;
	}
}
