package de.juli.jobfxclient.util;

public class TestStart {

	public static void main(String[] args) {
		System.out.println(AppProperties.getAppdocRootName());
		System.out.println(AppProperties.getAppName());
		System.out.println(AppProperties.getRootName());
		System.out.println(AppProperties.getOdtMailFlag());
		AppProperties.getOdtMailFlag().setValue(true);
		System.out.println(AppProperties.getOdtMailFlag());
	}
}
