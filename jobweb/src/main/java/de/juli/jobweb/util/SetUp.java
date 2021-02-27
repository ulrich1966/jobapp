package de.juli.jobweb.util;

import java.util.ArrayList;
import java.util.List;

import de.juli.jobmodel.controller.AccountController;
import de.juli.jobmodel.controller.SourceController;
import de.juli.jobmodel.model.Account;
import de.juli.jobmodel.model.Source;

public class SetUp {
	private static final String ACCOUT_NAME = "uli";
	private static List<Source> sources;
	private static List<Account> accounts;

	public static void main(String[] args) {
		sources = createSources();			
		sources.forEach(System.out::println);
		accounts = createAccouonts();
		accounts.forEach(System.out::println);
	}

	private static List<Account> createAccouonts() {
		AccountController controller = new AccountController();
		List<Account> accounts = new ArrayList<>();
		Account acc = new Account();
		acc.setName(ACCOUT_NAME);
		acc.setPass(ACCOUT_NAME);
		acc.setPort(587);
		acc.setProfillink("https://www.dropbox.com/s/8gj77t1xx47nkdc/profil_kloodt.pdf?dl=0");
		acc.setSender("ulrich.kloodt@gmx.de");
		acc.setUser("ulrich.kloodt@gmx.de");
		acc.setSmtp("mail.gmx.net");
		acc.setSmtpPass("Wrssdnuw@1966");
		accounts.add(acc);
		accounts.forEach(m -> controller.persist(m));
		return accounts;
	}

	private static List<Source> createSources() {
		SourceController controller = new SourceController();
		List<Source> sources = new ArrayList<>();
		sources.add(new Source("bei", "Xing.de"));
		sources.add(new Source("bei der", "Agentur für Arbeit"));
		sources.add(new Source("bei", "Step Stone"));
		sources.add(new Source("im", "Weser Kurier"));
		sources.forEach(m -> controller.persist(m));
		return sources;
	}
}
