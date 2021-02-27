package de.juli.jobmodel.util;

import java.util.concurrent.ThreadLocalRandom;

public class ToolService {

	public static int randomInt(int max) {
		return randomInt(0, max);
	}
	public static int randomInt(int min, int max) {
		return ThreadLocalRandom.current().nextInt(0, max);
	}
}
