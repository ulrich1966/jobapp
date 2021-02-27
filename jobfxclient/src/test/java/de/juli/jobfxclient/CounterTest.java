package de.juli.jobfxclient;

import org.junit.Test;

public class CounterTest {

	//@Test
	public void dec() {
		int start = 9505;
		int page = 634;
		for(;start >= 0; ) {
			start = start - 15;
			page--;
			System.out.println(String.format("%d %d", page, start));
		}
	}
	
	@Test
	public void asc() {
		int start = 0;
		int page = 1;
		int end = 162;
		for(;page <= end; ) {
			start = start + 15;
			page++;
			System.out.println(String.format("%d %d", page, start));
		}
	}

}
