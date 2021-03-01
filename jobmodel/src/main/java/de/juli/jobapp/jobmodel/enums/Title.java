package de.juli.jobapp.jobmodel.enums;

import java.util.ArrayList;
import java.util.List;

public enum Title {
	HERR("Herr", Sex.MALE),
	FRAU("Frau", Sex.FEMALE),
	HERR_DR("Herr Dr.", Sex.MALE),
	FRAU_DR("Frau Dr.", Sex.FEMALE),
	HERR_PROF("Herr Prof.", Sex.MALE),
	FRAU_PROF("Frau Prof.", Sex.FEMALE),
	LADIES_AND_GENTS("Damen und Herren", Sex.FEMALE);

	private String name = "";
	private Sex sex;

	Title(String name, Sex sex){
		this.name = name;
		this.sex = sex;
	}

	public static List<String> getNames(){
		List<String> names = new ArrayList<>();
		for (Title e : Title.values()) {
			names.add(e.getName());
		}
		return names;
	}

	public static Title titleByName(String value){
		for (Title e : Title.values()) {
			if(e.getName().equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}

	public static String appellation(Title title){
		String appellation = "geehrte";
		switch (title.getSex()) {
		case MALE:
			appellation = "geehrter";
			break;
		case FEMALE:
			appellation = "geehrte";
			break;
		default:
			break;
		}
		return appellation;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}
}
