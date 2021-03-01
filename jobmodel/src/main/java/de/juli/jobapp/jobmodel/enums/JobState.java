package de.juli.jobapp.jobmodel.enums;

import java.util.ArrayList;
import java.util.List;

public enum JobState {
	READY_TO_PERSIST("Bewerbung fertig zum Speichern"),
	CREATED("Bewerbung angelegt und in der Datenbank gespeichert"),
	DOC_CREATED("Dokumente erstellt"),
	SEND("Bewerbung versandt"),
	DOC_DELETED("Dokumente geloescht"),
	UPDATED("Aenderungen gespeichert");

	private String name;

	JobState(String name){
		this.name = name;
	}

	public static List<String> getNames(){
		List<String> names = new ArrayList<>();
		for (JobState e : JobState.values()) {
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
