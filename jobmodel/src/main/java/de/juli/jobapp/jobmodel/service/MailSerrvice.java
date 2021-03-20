package de.juli.jobapp.jobmodel.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.controller.ModelController;
import de.juli.jobapp.jobmodel.enums.AppHistory;
import de.juli.jobapp.jobmodel.model.History;
import de.juli.jobapp.jobmodel.model.Job;

public class MailSerrvice extends Thread{
	private static final Logger LOG = LoggerFactory.getLogger(MailSerrvice.class);
	private final Job currentJob;
	private final String mailUser;
	private final String mailPass;
	private boolean success;
	
	public MailSerrvice(Job job, String mailUser, String mailPass) {
		this.mailUser = mailUser;
		this.mailPass = mailPass;
		currentJob = job;
	}

	public String sendSimpleMail(String mailUser, String mailPass) throws EmailException {
		Email email = new SimpleEmail();
		email.setHostName(currentJob.getAccount().getSmtp());
		email.setSmtpPort(currentJob.getAccount().getPort());
		email.setSSL(true);
		email.setAuthenticator(new DefaultAuthenticator(mailUser, mailPass));
		email.setFrom(currentJob.getAccount().getSender());
		email.setSubject("TestMail");
		email.setMsg("Moin");
		email.addTo(currentJob.getCompany().getContact().getEmail());
		//email.addCc(recipients.get(0));
		email.send();
		LOG.info("DONE {} : {}", email.getHostName(), email.getSmtpPort());
		return null;
	}

	public boolean sendAttatchmentMail(String mailUser, String mailPass) throws EmailException, IOException {
		MultiPartEmail email = new MultiPartEmail();
		EmailAttachment apply = new EmailAttachment();
		EmailAttachment profil = new EmailAttachment();
		
		Path pathToLetter = Paths.get(currentJob.getPdf().getTarget());
		Path pathToVita = Paths.get(currentJob.getVita().getTarget());
		Path pathToMail = Paths.get(currentJob.getEmail().getTarget());
		
		StringBuilder sb = new StringBuilder();

		if (pathToLetter == null || pathToMail == null || pathToMail == null) {
			return false;
		}

		if (pathToLetter.toFile().exists()) {
			apply.setPath(pathToLetter.toString());
			apply.setDisposition(EmailAttachment.ATTACHMENT);
			apply.setDescription("");
			apply.setName(String.format("%s.%s", "anschreiben",  currentJob.getPdf().getExtension()));
			sb.append(pathToLetter.toString() + "\n");
		} else {
			LOG.error("Anschreiben konnte nicht erzeugt werden. Datei {}  nicht gefunden", currentJob.getPdf().getTarget());
			return false;
		}

		if (pathToVita.toFile().exists()) {
			profil.setPath(pathToVita.toString());
			profil.setDisposition(EmailAttachment.INLINE);
			profil.setDescription("Profil");
			profil.setName(String.format("%s.%s", "lebenslauf",  currentJob.getVita().getExtension()));
			sb.append(pathToVita.toString() + "\n");
		} else {
			LOG.error("Anschreiben konnte nicht erzeugt werden. Datei {}  nicht gefunden", currentJob.getVita().getTarget());
			return false;
		}

		if (pathToMail.toFile().exists()) {
			List<String> lines = Files.readAllLines(pathToMail, StandardCharsets.UTF_8);
			StringBuilder msg = new StringBuilder();
			lines.forEach(line -> msg.append(line+"\n"));

			email.setHostName(currentJob.getAccount().getSmtp());
			email.setSmtpPort(currentJob.getAccount().getPort());
			email.setSSL(true);
			email.setAuthenticator(new DefaultAuthenticator(mailUser, mailPass));
			email.addTo(currentJob.getCompany().getContact().getEmail());
			email.setFrom(currentJob.getAccount().getSender());
			email.setSubject(currentJob.getTitle());
			email.setMsg(msg.toString());

			sb.append(pathToMail.toString() + "\n");
		} else {
			LOG.error("Anschreiben konnte nicht erzeugt werden. Datei {}  nicht gefunden", currentJob.getEmail().getTarget());
			return false;
		}

		if (email != null) {
			LOG.info("Attatchmments {}", sb.toString());
			email.attach(apply);
			email.attach(profil);
			String send = "";
			try {
				send = email.send();
				LOG.info("Send Email {}", send);
				currentJob.addHistory(new History(AppHistory.SEND));
				final ModelController conroller = new ModelController();
				conroller.update(currentJob);
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	@Override
	public void run() {
		LOG.info("Email sending STARTED");
		success = false;
		try {
			success = sendAttatchmentMail(this.mailUser, this.mailPass);
			LOG.info("Email sending successful: {}", success);
		} catch (EmailException | IOException e) {
			e.printStackTrace();
			LOG.info("Email sending FAILED");
		}
	}

	public boolean isSuccess() {
		return success;
	}
}
