package de.juli.jobweb;

import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

public class Console {

	@Test
	public void test() throws IOException {
        String[] cmdArray = new String[2];
        cmdArray[0] = "E:\\development\\db_servers\\mariadb-10.3.10-winx64\\bin\\mysqld.exe ";
        cmdArray[1] = "--console";
        Process process = Runtime.getRuntime().exec(cmdArray,null);
        boolean alive = process.isAlive();

        // print another message
        System.out.println("alive: "+alive);
        
        Scanner scanner = new Scanner(System.in);

        while (true) {

            System.out.print("Enter something : ");
            String input = scanner.nextLine();

            if ("q".equals(input)) {
                System.out.println("Exit!");
                break;
            }

            System.out.println("input : " + input);
            System.out.println("-----------\n");
        }

        scanner.close();

       
	}

}
