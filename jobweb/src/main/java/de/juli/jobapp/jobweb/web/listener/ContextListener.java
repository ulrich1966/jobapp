package de.juli.jobapp.jobweb.web.listener;

import javax.persistence.NoResultException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.controller.AccountController;
import de.juli.jobapp.jobmodel.controller.AppSettingController;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.model.AppSetting;
import de.juli.jobapp.jobmodel.util.AccoundHelper;
import de.juli.jobapp.jobweb.exeptions.ShittHappensExeption;
import javassist.NotFoundException;

@WebListener
public class ContextListener implements ServletContextListener {
	private static final Logger LOG = LoggerFactory.getLogger(ContextListener.class);
	private AccountController controller = new AccountController();
	private AppSettingController settingController = new AppSettingController();

	/**
	 * Wird nach dem App-Atart aufgerufen und prueft ob es einen Accounds fuer
	 * Benutzer gibt. Wenn nicht, weren weleche angelegt, damit man sich
	 * einloggen kann und die die App benutzt werden kann.
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		LOG.debug("{} Starting up!", this.getClass().getName());
		AppSetting settings = null;
		
		String name = "admin";
		if(!checkAccount(name)) {
			throw new ShittHappensExeption(String.format("Das Ersestellen eines Accounds fuer %s ist voll in die Hose gegangen", name));
		}
		name = "uli";
		if(!checkAccount(name)) {
			throw new ShittHappensExeption(String.format("Das Ersestellen eines Accounds fuer %s ist voll in die Hose gegangen", name));
		}

		try {
			settings = settingController.findFirst();
		} catch (NoResultException e) {
			settings = new AppSetting();
			settings.setLibreOfficeHome("C:\\Program Files (x86)\\LibreOffice 5\\program");
			settings.setCmd("soffice.exe");
			settingController.persist(settings);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {
		// DbServerStart.getInstance().stop();;
		LOG.debug("{} Shutting down!", this.getClass().getName());
	}

	/**
	 * Pruefen auf Vorhandensein eines Accounds angegebenen Namens oder Estellen der Accounds 
	 * mit angebebenen Namen wenn nicht vorhanden.
	 */
	private boolean checkAccount(String name) {
		Account account = null;
		boolean success = false;
		try {
			account = controller.findByName(name);
			success = true;
		} catch (NoResultException e) {
			LOG.debug("Account wurde nicht gefunden!\n{}", e.getMessage());
			try {
				account = AccoundHelper.getInstance().fillAccoundByProperties(name);
				controller.persist(account);
				success = true;
			} catch (NotFoundException e1) {
				LOG.debug("Eine Propertiy fuer den Account wurde nicht gefunden!\n{}", e1.getMessage());
				e1.printStackTrace();
			} catch (Exception e2) {
				LOG.error("{}", e2);
			}
		}
		return success;
	}
}
