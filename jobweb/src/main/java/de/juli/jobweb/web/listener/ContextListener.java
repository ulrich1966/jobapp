package de.juli.jobweb.web.listener;

import javax.persistence.NoResultException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import de.juli.jobmodel.controller.AccountController;
import de.juli.jobmodel.controller.AppSettingController;
import de.juli.jobmodel.model.Account;
import de.juli.jobmodel.model.AppSetting;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println(this.getClass().getName()+" Starting up!");
        AccountController controller = new AccountController();
        AppSettingController settingController = new AppSettingController();
        Account admin;
        Account uli;

        AppSetting setting;
		try {
			admin = controller.findByName("admin");
		} catch (NoResultException e) {
			admin = new Account();
			admin.setName("admin");
			admin.setPass("admin");
			admin.setProfillink("https://www.dropbox.com/s/8gj77t1xx47nkdc/profil_kloodt.pdf?dl=0");
			admin.setPort(587);
			admin.setSender("ulrich.kloodt@gmx.de");
			admin.setSmtp("mail.gmx.net");
			admin.setSmtpPass("Wrssdnuw@1966");
			admin.setUser("ulrich.kloodt@gmx.de");
			controller.persist(admin);
		}
		
		try {
			uli = controller.findByName("uli");
		} catch (NoResultException e) {
			uli = new Account();
			uli.setName("uli");
			uli.setPass("uli");
			uli.setProfillink("https://www.dropbox.com/s/8gj77t1xx47nkdc/profil_kloodt.pdf?dl=0");
			uli.setPort(587);
			uli.setSender("ulrich.kloodt@gmx.de");
			uli.setSmtp("mail.gmx.net");
			uli.setSmtpPass("Wrssdnuw@1966");
			uli.setUser("ulrich.kloodt@gmx.de");
			controller.persist(uli);
		}
		try {
			setting = settingController.findFirst();
		} catch (NoResultException e) {
			setting = new AppSetting();
			setting.setLibreOfficeHome("C:\\Program Files (x86)\\LibreOffice 5\\program");
			setting.setCmd("soffice.exe");
			settingController.persist(setting);
		}
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    	//DbServerStart.getInstance().stop();;
        System.out.println(this.getClass().getName()+" Shutting down!");
    }
}
