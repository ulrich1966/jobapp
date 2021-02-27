package de.juli.jobweb;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.After;
import org.junit.Test;

public class Date {

	private LocalDate parse;

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		String value = "Thu Oct 11 12:00:00 CEST 2018";
		parse = LocalDate.parse(value, DateTimeFormatter.ISO_DATE_TIME);
		System.out.println(parse.toString());
	}

}
