package de.juli.jobfxclient;

import java.awt.Desktop;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.junit.After;
import org.junit.Test;

public class MailTest {

	static String test = "hi";
	private static String attachment;
	private static String to;
	private static String cc;
	private static String subject;
	private static String body;

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() throws Exception {

		List<String> lines = Files.readAllLines(Paths.get("M:/Bewerbung/Bewerbung2018/name_10/email.txt"), StandardCharsets.UTF_8);
		StringBuilder sb = new StringBuilder();
		lines.forEach(l -> sb.append(String.format("%s\n", l)));
	
		String mail = "john@example.com";
		String subject = "hello";
		String body = sb.toString();
		//String body = "M:\\Bewerbung\\Bewerbung2018\\name_10\\email.txt";
		body = URLEncoder.encode(body, "UTF-8").replace("+", "%20");
		String attachment="'file:///M:/Bewerbung/Bewerbung2018/name_10/anschreiben.pdf'";
		
		//"mailto:a@gmail.com?subject=my report&body=see attachment&attachment=c:\myfolder\myfile.txt"
		/*
		 *mailto:joe@example.com,jane@example.com?subject=hello&body=How%20are%20you%3F 
		 */
		//G:/temp/anschreiben.pdf
		Desktop desktop;
		if (Desktop.isDesktopSupported() && (desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
			URI mailto = new URI("mailto:"+mail+"?subject="+subject+"&body="+body+"&attachment=file:///G:/temp/anschreiben.pdf");
			//URI mailto = new URI("mailto:"+mail+"?subject="+subject+"&body="+body+"");
			desktop.mail(mailto);
		} else {
			throw new RuntimeException("desktop doesn't support mailto; mail is dead anyway ;)");
		}
//mailto:john@example.com?subject=hello&body=Sehr%20geehrter%20Herr%20Prof.%20name_10_lastName_1%2C%0A%0AAugrund%20ihrer%20Stellenausscheibung%20vom%202018-05-17%20bei%20Xing.de%2C%0Asende%20ich%20Ihen%20anbei%20meine%20Bewerbunsunterlagen%20f%C3%BCr%20die%20Position%20als%20%0Aname_10_title_10%20im%20Anhang%20dieser%20Mail%20zu.%0A%0AMit%20freundlichem%20Gru%C3%9F%0A%0AUlrich%20Kloodt%0A%0A&attachment=G:\temp\anschreiben.odt

	}

	public void test2() throws Exception {
		// attachment = "F:\\pietquest.png";
		// to = "test@test.de";
		// cc = "a.b@c.de";
		// subject = "TestSubject 123";
		// body = "Hi, what\'s going on%0D%0Anew line";
		//
		// body = replace(body);
		// subject = replace(subject);
		//
		// String[] value =
		// WindowsRegistry.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Clients\\Mail",
		// "");
		//
		// if (value[10].contains("Thunderbird")) {
		// System.out.println("Thunderbird");
		// String[] pfad =
		// WindowsRegistry.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Clients\\Mail\\Mozilla
		// Thunderbird\\shell\\open\\command", "");
		// String Pfad = pfad[10] + " " + pfad[11];
		// String argument = Pfad + " /compose \"to=" + to + ",cc=" + cc +
		// ",subject=" + subject + ",body=" + body + ",attachment=" + attachment
		// + "\"";
		// // System.out.println(argument);
		// try {
		// Runtime.getRuntime().exec(argument);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// } else if (value[10].contains("Outlook")) {
		// System.out.println("Outlook");
		// String[] pfad =
		// WindowsRegistry.readRegistry("HKEY_LOCAL_MACHINE\\SOFTWARE\\Clients\\Mail\\Microsoft
		// Outlook\\shell\\open\\command", "");
		// String Pfad = pfad[10];
		// String argument = Pfad + " /a " + attachment + " /m \"" + to + "&cc="
		// + cc + "&subject=" + subject + "&body=" + body + "\"";
		// // System.out.println(argument);
		// try {
		// Runtime.getRuntime().exec(argument);
		// } catch (IOException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
	}

	public static String replace(String toReplace) {
		toReplace = toReplace.replace(" ", "%20");
		toReplace = toReplace.replace(",", "%2C");
		toReplace = toReplace.replace("?", "%3F");
		toReplace = toReplace.replace(".", "%2E");
		toReplace = toReplace.replace("!", "%21");
		toReplace = toReplace.replace(":", "%3A");
		toReplace = toReplace.replace(";", "%3B");
		return toReplace;
	}

}
