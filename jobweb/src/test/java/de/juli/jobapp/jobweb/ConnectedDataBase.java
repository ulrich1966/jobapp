package de.juli.jobapp.jobweb;

import org.junit.Test;

import de.juli.jobapp.jobmodel.controller.EmController;

public class ConnectedDataBase {

	@Test
	public void test() {
		String db = EmController.getDb();
		String dbUser = EmController.getDbUser();
		
		System.out.println(db);
		System.out.println(dbUser);
	}

}
