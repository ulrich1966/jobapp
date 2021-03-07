package de.juli.jobapp.jobweb.web.listener;

import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.controller.EmController;
import de.juli.jobapp.jobmodel.controller.ModelController;
import de.juli.jobapp.jobmodel.controller.PersistenceController;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.AppSetting;
import de.juli.jobapp.jobmodel.model.Job;
import de.juli.jobapp.jobmodel.service.JsonService;
import de.juli.jobapp.jobmodel.util.AppProperties;
import de.juli.jobapp.jobweb.util.SetUpDataBase;
import javassist.NotFoundException;

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
		SetUpDataBase setUp = SetUpDataBase.getInstance();
		
		LOG.debug("Einstellungen der Persistence-Unit:");
		EmController.getEm().getProperties().entrySet().forEach(e -> LOG.debug("{} : {}", e.getKey(), e.getValue()));

		if (setUp.tableIsEmty(Account.class)) {
			AppProperties properties = AppProperties.getInstance(AppProperties.CONFIG_PROP);
			ModelController modelController = new ModelController();
			JsonService jsonService = new JsonService();
			
			try {
				String usersFileName = properties.propertyFind("json.data.users");
				List<Account> accounts = jsonService.<Account>readList(Account.class, usersFileName);
				accounts.forEach(e -> modelController.create(e));
				LOG.debug("{}", "Ein paar Benutzerdaten wurden neu angelegt!");
				
				if(setUp.tableIsEmty(AppSetting.class)) {
					AppSetting setting = new AppSetting();
					PersistenceController<AppSetting> settingController = new PersistenceController<>();
					setting.setLibreOfficeHome(properties.propertyFind("libreoffice.home"));
					setting.setCmd(properties.propertyFind("libreoffice.cmd"));
					settingController.create(setting);
				}
				
				if(setUp.tableIsEmty(Job.class) && Boolean.valueOf(properties.propertyFind("db.data.job.setup"))) {
					String jobFileName = properties.propertyFind("json.data.jobs");
					List<Job> jobs = jsonService.<Job>readList(Job.class, jobFileName);
					accounts.forEach(a -> {
						try {
							if(a.getName().equals(properties.propertyFind("main.account.name"))){
								jobs.forEach(j -> {
									a.addJob(j);									
									modelController.create(a);								
								});
							}
						} catch (NotFoundException e1) {
							LOG.error("{}", "Das Erstellen Jobs ist in die Hose gegangen!");
							LOG.error("{}", e1);
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

	/**
	 * Pruefen auf Vorhandensein eines Accounds angegebenen Namens oder Estellen
	 * der Accounds mit angebebenen Namen wenn nicht vorhanden.
	 */
	// @SuppressWarnings("unused")
	// @Deprecated
	// private boolean checkAccount(String name) {
	// AccountController controller = new AccountController();
	// Account account = null;
	// boolean success = false;
	// try {
	// account = controller.findByName(name);
	// success = true;
	// } catch (NoResultException e) {
	// LOG.debug("Account wurde nicht gefunden!\n{}", e.getMessage());
	// try {
	// account = AccountHelper.getInstance().fillAccoundByProperties(name);
	// controller.persist(account);
	// // TODO das sollte mit einer Abfrage der Einstellung in er
	// // persitence.xml 'hibernate.hbm2ddl.auto' passieren
	// SetUpDataBase.getInstance().create(account);
	// success = true;
	// } catch (NotFoundException e1) {
	// LOG.debug("Eine Propertiy fuer den Account wurde nicht gefunden!\n{}",
	// e1.getMessage());
	// e1.printStackTrace();
	// } catch (Exception e2) {
	// LOG.error("{}", e2);
	// }
	// }
	// return success;
	// }
}
