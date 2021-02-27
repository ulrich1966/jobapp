package de.juli.jobfxclient.controller;

import de.juli.jobmodel.controller.CompanyController;
import de.juli.jobmodel.controller.ContactController;
import de.juli.jobmodel.controller.DocumentController;
import de.juli.jobmodel.controller.JobController;

public abstract class AbstController {
	protected DocumentController docController = new DocumentController();
	protected JobController jobController = new JobController();
	protected CompanyController companyController = new CompanyController();
	protected ContactController contactController = new ContactController();
}
