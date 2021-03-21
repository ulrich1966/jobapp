package de.juli.jobapp.jobweb.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.Job;

/**
 * Streuert das Versenden der E-Mails
 */
public class SendService {
	private static final Logger LOG = LoggerFactory.getLogger(SendService.class);
	private final MailSerrvice service;

	/**
	 * Costructor konfiguriert den MailService und setzt E-Mail- Empfaenger und
	 * Sender
	 */
	public SendService(Job job, String mailUser, String mailPass) {
		List<String> recipients = new ArrayList<>();
		Account acc = job.getAccount();

		String resip = job.getCompany().getContact().getEmail();
		recipients.add(resip);
		recipients.add(acc.getSender());
		service = new MailSerrvice(job, mailUser, mailPass);
	}

	/**
	 * Sartet den Service (run()) und wartet bis der Tread beendet ist.
	 * Gibt die Statusmeldung true, wenn allles gut gegangen oder false,
	 * wenn was scheif gelaufen ist zurueck.
	 */
	public boolean send() throws Exception {
		service.start();
		service.join(10000);
		if (!service.isAlive()) {
			LOG.debug("Der E-Mail Versandt ist beendet!");
		} 
		return service.isSuccess();
	}

}
