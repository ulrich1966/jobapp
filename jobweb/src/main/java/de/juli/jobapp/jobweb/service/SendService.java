package de.juli.jobapp.jobweb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.juli.jobmodel.model.Account;
import de.juli.jobmodel.model.Job;
import de.juli.jobmodel.service.MailSerrvice;

public class SendService  {
	private final MailSerrvice service;

	public SendService(Job job) {
		Map<String, Object> data = new HashMap<>(); 
		List<String> recipients = new ArrayList<>();
		Account acc = job.getAccount();

		String resip = job.getCompany().getContact().getEmail();
		recipients.add(resip);		
		recipients.add(acc.getSender());
		
		
		
		service = new MailSerrvice(acc, data, job);
	}

	public boolean send() {
		Thread thread = new Thread(service);
		thread.start();
		System.out.println("DONE");
		return true;
	}

}
