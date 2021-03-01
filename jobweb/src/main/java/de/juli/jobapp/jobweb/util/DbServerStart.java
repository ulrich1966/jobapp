package de.juli.jobapp.jobweb.util;

import java.sql.SQLException;

import org.h2.tools.Server;

public class DbServerStart {
	private static Server server;
	private static Server webServer;
	private static DbServerStart instance;
	private static final String[] ARGS = new String[] {"-baseDir", "~/job"};

	private DbServerStart() {
	}

	public static DbServerStart getInstance() {
		if (instance == null) {
			instance = new DbServerStart();
		}
		return instance;
	}

	public void start() {
		try {
			try {
				if (server == null && !isRunning()) {
					server = Server.createTcpServer(ARGS).start();
					System.out.println("");
					System.out.println("H2 Db Server runs ...");
					System.out.println("... on port: " + server.getPort());
					System.out.println("... with status: " + server.getStatus());
					System.out.println("... on url: " + server.getURL());
					System.out.println("");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
			server.stop();
		}
	}
	
	public String startWebConsole() {
		String url = null;
		try {
			webServer = Server.createWebServer(ARGS).start();
			System.out.println("");
			System.out.println("H2 Db Server runs ...");
			System.out.println("... on port: " + webServer.getPort());
			System.out.println("... with status: " + webServer.getStatus());
			System.out.println("... on url: " + webServer.getURL());
			System.out.println("");
			url = webServer.getURL();
			Server.openBrowser(webServer.getURL());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return url;
	}
	
	public void stopWebServer() {
		webServer.stop();		
	}
	
	public boolean isConsole() {
		if(webServer != null) {
			return webServer.isRunning(false);
		}
		return false;
	}

	public void stop() {
		if (server != null && isRunning()) {
			server.stop();
			System.out.println("");
			System.out.println("H2 Db Server stoped!");
			System.out.println("");
		}
		if (webServer != null) {
			webServer.stop();
			System.out.println("");
			System.out.println("H2 Db WebServer stoped!");
			System.out.println("");
		}
	}

	public boolean isRunning() {
		if(server != null) {
			return server.isRunning(false);
		}
		return false;
	}
	
	public Server getServer() {
		return server;
	}
}
