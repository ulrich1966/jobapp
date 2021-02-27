package de.juli.jobfxclient;

import java.math.BigDecimal;

import org.junit.Test;

public class Reverse {

	@Test
	public void test() {
		BigDecimal dec = new BigDecimal(10000);

		String string = String.valueOf(dec);

		System.out.println(string);
	}

}
