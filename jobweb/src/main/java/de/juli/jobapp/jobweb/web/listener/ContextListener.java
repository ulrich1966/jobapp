package de.juli.jobapp.jobweb.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobapp.jobmodel.controller.EmController;
import de.juli.jobapp.jobmodel.model.Account;
import de.juli.jobapp.jobmodel.util.AppProperties;
import de.juli.jobapp.jobweb.util.SetUpDataBase;

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
		String name = "userList";
		AppProperties properties = AppProperties.getInstance(AppProperties.CONFIG_PROP);

		EmController.getEm().getProperties().entrySet().forEach(e -> LOG.debug("{} : {}", e.getKey(), e.getValue()));
		
		SetUpDataBase setUp = SetUpDataBase.getInstance();

		
		if (setUp.tableIsEmty(Account.class)) {
			if(setUp.persitSomeAccounds()) {
				LOG.debug("{}", "Ein paar Benutzerdaten wurden neu angelegt!");
			} else {
				LOG.debug("{}", "Das Erstellen von Benutzerdaten war nicht moeglich!");
			}
		}

//		size = controllerSetting.getTableSize(AppSetting.class);
//		if (size != null) {
//			LOG.debug("Datacount: {}", size);
//		} else {
//			throw new ShittHappensExeption("Das Ersestellen der Settings ist voll in die Hose gegangen\n" + "Die Anzahl der Datensaetze in der Tabelle konnte nicht ermittelt werden");
//		}
//		if (size == 0) {
//			try {
//			AppProperties props = AppProperties.getInstance(AppProperties.CONFIG_PROP);
//			AppSetting setting = new AppSetting();
//			setting.setLibreOfficeHome(props.propertyFind("libreoffice.home"));
//			setting.setCmd(props.propertyFind("libreoffice.cmd"));
//			} catch (Exception e) {
//				LOG.error("{}", e);				
//			}
//		}

		// String name = "admin";
		// if(!checkAccount(name)) {
		// throw new ShittHappensExeption(String.format("Das Ersestellen eines
		// Accounds fuer %s ist voll in die Hose gegangen", name));
		// }
		// name = "uli";
		// if(!checkAccount(name)) {
		// throw new ShittHappensExeption(String.format("Das Ersestellen eines
		// Accounds fuer %s ist voll in die Hose gegangen", name));
		// }

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
//	@SuppressWarnings("unused")
//	@Deprecated
//	private boolean checkAccount(String name) {
//		AccountController controller = new AccountController();
//		Account account = null;
//		boolean success = false;
//		try {
//			account = controller.findByName(name);
//			success = true;
//		} catch (NoResultException e) {
//			LOG.debug("Account wurde nicht gefunden!\n{}", e.getMessage());
//			try {
//				account = AccountHelper.getInstance().fillAccoundByProperties(name);
//				controller.persist(account);
//				// TODO das sollte mit einer Abfrage der Einstellung in er
//				// persitence.xml 'hibernate.hbm2ddl.auto' passieren
//				SetUpDataBase.getInstance().create(account);
//				success = true;
//			} catch (NotFoundException e1) {
//				LOG.debug("Eine Propertiy fuer den Account wurde nicht gefunden!\n{}", e1.getMessage());
//				e1.printStackTrace();
//			} catch (Exception e2) {
//				LOG.error("{}", e2);
//			}
//		}
//		return success;
//	}
}
