package de.juli.jobapp.jobweb.web.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.controller.CriteriaController;
import de.juli.jobapp.jobmodel.controller.EmController;
import de.juli.jobapp.jobmodel.controller.ModelController;
import de.juli.jobapp.jobmodel.exeptions.ShitHappendsExeption;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.AppSetting;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.service.JsonService;
import de.juli.jobapp.jobmodel.util.AppProperties;

@WebListener
public class ContextListener implements ServletContextListener {
	private static final Logger LOG = LoggerFactory.getLogger(ContextListener.class);

	/**
	 * Wird nach dem App-Atart aufgerufen. Gibt die Entetymanager Propüerties
	 * aus der pessistence.xml aus. Prueft ob es einen Accounds fuer Benutzer
	 * gibt. Wenn nicht, weren weleche angelegt, damit man sich einloggen kann
	 * und die die App benutzt werden kann. Wenn es keinen Beutzer gibt, gib es
	 * auch keine Bewerbungnen. Um die diese zu erstellen wird eine
	 * entsperechender Handler aufgerufen.
	 */
	@Override
	public void contextInitialized(ServletContextEvent context) {
		LOG.debug("{} Starting up!", this.getClass().getName());
		CriteriaController cc = new CriteriaController();
		
		LOG.debug("Einstellungen der Persistence-Unit:");
		EmController.getEm().getProperties().entrySet().forEach(e -> LOG.debug("{} : {}", e.getKey(), e.getValue()));
		AppProperties properties = AppProperties.getInstance(AppProperties.CONFIG_PROP);
		
		try {
			if(!new Boolean(properties.propertyFind("db.data.job.setup"))) {
				return;
			}
		} catch (ShitHappendsExeption e) {
			LOG.error("{}", e.getMessage());
		}

		if (cc.getTableSize(Account.class) == 0) {
			ModelController controller = new ModelController();
			JsonService jsonService = new JsonService();
			
			try {
				String usersFileName = properties.propertyFind("json.data.users");
				List<Account> accounts = jsonService.<Account>readList(Account.class, usersFileName);
				accounts.forEach(e -> controller.create(e));
				LOG.debug("{}", "Ein paar Benutzerdaten wurden neu angelegt!");
				
				if(cc.getTableSize(AppSetting.class) == 0) {
					AppSetting setting = new AppSetting();
					setting.setLibreOfficeHome(properties.propertyFind("libreoffice.home"));
					setting.setCmd(properties.propertyFind("libreoffice.cmd"));
					controller.create(setting);
				}
				
				if(cc.getTableSize(Job.class) == 0 && Boolean.valueOf(properties.propertyFind("db.data.job.setup"))) {
					String jobFileName = properties.propertyFind("json.data.jobs");
					List<Job> jobs = jsonService.<Job>readList(Job.class, jobFileName);
					accounts.forEach(a -> {
						try {
							if(a.getName().equals(properties.propertyFind("main.account.name"))){
								jobs.forEach(j -> {
									a.addJob(j);									
									controller.create(a);								
								});
							}
						} catch (ShitHappendsExeption e) {
							LOG.error("{}", "Das Erstellen Jobs ist in die Hose gegangen!");
							LOG.error("{}", e);
						}
					});
					LOG.debug("{}", "Ein paar Jobs wurden neu angelegt!");
				} 
				
			} catch (Exception e) {
				LOG.error("{}", "Das Erstellen von Accounds ist in die Hose gegangen!");
				LOG.error("{}", e);
			}
		}

		LOG.debug("{}", "contextInitialized DONE!");
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// DbServerStart.getInstance().stop();;
		LOG.debug("{} Shutting down!", this.getClass().getName());
	}
}
