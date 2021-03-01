package de.juli.jobapp.jobmodel.util;
import java.sql.SQLException;
import java.util.Scanner;

import org.h2.tools.Server;

public class DbServerStart {

	public static void main(String[] args) throws SQLException {
		Server server = Server.createTcpServer(args).start();
		Scanner scan = new Scanner(System.in);
		System.out.println("Server runs ... stop press q");
		while (scan.hasNext()) {
			String line = scan.nextLine();
			if(line != null && !line.isEmpty() && line.contains("q")) {
				System.out.println("Stop ");
				server.stop();
				scan.close();
				System.exit(0);
			}
		}
	}
}