package de.juli.jobapp.jobmodel.enums;

import java.util.ArrayList;
import java.util.List;

public enum AppHistory {
	NOT_SEND("Unterlagen erstellt aber noch nicht verdandt"),
	SEND("Bewerbungsunterlagen versandt"),
	REPLY("Es gab eine Antwort auf die Bewerbung"),
	INVITATION("Von der Firmal wurde Einladung erhalten"),
	INTERVIEW("In der Firma ist eine Vorstellung erfolgt"),
	SUCCESS("Es kamm zur Einstllung"),
	REFUSED("Es wurde eine Absage erhalten");

	private String name;

	AppHistory(String name){
		this.name = name;
	}

	public static List<String> getNames(){
		List<String> names = new ArrayList<>();
		for (AppHistory e : AppHistory.values()) {
			names.add(e.getStateName());
		}
		return names;
	}

	public String getStateName() {
		return name;
	}

	public void setStateName(String stateName) {
		this.name = stateName;
	}
}
