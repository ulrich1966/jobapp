package de.juli.jobfxclient;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobfxclient.service.MailSerrvice;

public class SmtpTest {
	private static final Logger LOG = LoggerFactory.getLogger(SmtpTest.class);

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		MailSerrvice service = new MailSerrvice();
		//service.sendSimpleMail();
		Map<String, Path> attatchs = new HashMap<>();
		attatchs.put("apply", Paths.get("G:/temp/name_10/anschreiben.pdf"));
		attatchs.put("profil", Paths.get("G:/myFiles/Bewerbung2018/shortprof_kloodt.pdf"));
		attatchs.put("email", Paths.get("G:/temp/name_10/email.txt"));
		boolean success = service.sendAttatchmentMail(attatchs, "anschreiben_kloodt.pdf", "kurzprofil_kloodt.pdf");
		if(success) {
			LOG.info("DONE!");
		} else {
			LOG.error("FAIL!");			
		}
	}

}
