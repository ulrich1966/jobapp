package de.juli.jobweb.util;

import java.util.ArrayList;
import java.util.List;

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

	/**
	 * Uebernimmt der Contextlistener 
	 */
	private static List<Account> createAccouonts() {
		return null;
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
