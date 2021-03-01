package de.juli.jobapp.jobmodel.enums;

public enum FileTyps {
	DOC("doc"),
	DOCX("docx"),
	ODT("odt"),
	TXT("txt");

	private String name;

	FileTyps(String name){
		this.name = name;
	}

	public static FileTyps fildByTypeName(String name){
		for (FileTyps type : FileTyps.values()) {
			if(type.getName().equals(name)){
				return type;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
