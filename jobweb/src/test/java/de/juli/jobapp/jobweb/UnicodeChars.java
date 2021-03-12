package de.juli.jobapp.jobweb;

import org.junit.Test;

import de.juli.jobapp.jobmodel.enums.Uml;

public class UnicodeChars {

	@Test
	public void test() {
		System.out.println("\u00C4");
		System.out.println("\u00D6");
		System.out.println("\u00DC");
		System.out.println("\u00E4");
		System.out.println("\u00F6");
		System.out.println("\u00FC");
		System.out.println("\u00DF");
		
		System.out.println(Uml.A_UML.getName());
		System.out.println(Uml.a_UML.getName());
		System.out.println(Uml.O_UML.getName());
		System.out.println(Uml.o_UML.getName());
		System.out.println(Uml.U_UML.getName());
		System.out.println(Uml.u_UML.getName());
		System.out.println(Uml.SZ.getName());
		
	}

}
