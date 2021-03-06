package de.juli.jobapp.jobmodel.enums;

import java.util.ArrayList;
import java.util.List;

public enum JobState {
	READY_TO_PERSIST("Bewerbung fertig zum Speichern"),
	CREATED("Bewerbung angelegt und in der Datenbank gespeichert"),
	DOC_CREATED("Dokumente erstellt"),
	SEND("Bewerbung versandt"),
	DOC_DELETED("Dokumente gel"+Uml.o_UML.getUchar()+"scht"),
	UPDATED(Uml.A_UML.getUchar()+"nderungen gespeichert"),
	PDF_CREATED("Pdf mit Template erzeugt");

	private String name;

	JobState(String name){
		this.name = name;
	}

	public static List<String> getNames(){
		List<String> names = new ArrayList<>();
		for (JobState e : JobState.values()) {
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
