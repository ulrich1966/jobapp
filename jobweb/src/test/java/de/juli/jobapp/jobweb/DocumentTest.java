package de.juli.jobapp.jobweb;

import org.junit.Test;

import de.juli.jobapp.jobmodel.model.Email;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.model.Letter;
import de.juli.jobapp.jobmodel.model.Vita;
import de.juli.jobapp.jobmodel.service.PersistService;

public class DocumentTest {

	@Test
	public void test() {
		PersistService persistService = new PersistService();
//		DocumentController documentController = persistService.getDocumentController();
//		documentController.persist(new Vita("Bla", "Blub"));
//		documentController.persist(new Letter("Bla", "Blub"));
//		documentController.persist(new Email("Bla", "Blub"));
		
		Job job = persistService.getJobController().findById(1l);
		
		job.setLetter(new Letter("Bla", "Blub"));
		job.setVita(new Vita("Bla", "Blub"));
		job.setEmail(new Email("Bla", "Blub"));
		
		persistService.getJobController().persist(job);
		
	}

}
