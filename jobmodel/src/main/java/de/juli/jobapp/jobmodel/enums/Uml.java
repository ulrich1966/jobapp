package de.juli.jobapp.jobmodel.enums;

public enum Uml {
	A_UML("\u00C4"),
	O_UML("\u00D6"),
	U_UML("\u00DC"),
	a_UML("\u00E4"),
	o_UML("\u00F6"),
	u_UML("\u00FC"),
	SZ("\u00DF");
	
	private String uchar;

	Uml (String name){
		this.uchar = name;
	}
	
	public String getUchar() {
		return uchar;
	}

	public void setUchar(String uchar) {
		this.uchar = uchar;
	}

}