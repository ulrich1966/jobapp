package de.juli.jobapp.jobweb;

import org.h2.tools.Server;
import org.junit.After;
import org.junit.Test;

public class DbServerTest {
	public static String DB_FILE_LOCATION = "./job.mv.db";

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {
		   Server server = Server.createWebServer();
		   server.start();
		   Server.openBrowser(server.getURL());
	}

}
