package de.juli.jobfxclient.viewmodel;

public enum Conf {
	E_MAIL("E-Mail"),
	PATHS("Programmpfade"),
	DATABASE("Datenbank"),
	DOCUMENTS("Dokumente");

	private String name;

	Conf(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String toString(){
		return name;
	}
}
