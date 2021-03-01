package de.juli.jobapp.jobmodel.model;

import javax.persistence.Entity;

@Entity
public class Pdf extends Document {
	
	public Pdf() {
		super();
	}
	
	public Pdf(String name) {
		super.setName(name);
		setExtension(name);
	}
	
	public Pdf(String template, String name) {
		this(name);
		super.setTemplate(template);
	}
}
