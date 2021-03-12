package de.juli.jobapp.jobmodel.enums;

import java.util.ArrayList;
import java.util.List;

public enum AppHistory {
	NOT_BUILD("Unterlagen sind nicht erstellt oder gel"+Uml.o_UML.getName()+"scht"),
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
			names.add(e.name);
		}
		return names;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
