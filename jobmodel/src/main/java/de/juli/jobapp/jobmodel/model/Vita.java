package de.juli.jobapp.jobmodel.model;

import javax.persistence.Entity;

@Entity
public class Vita extends Document {
	
	public Vita() {
		super();
	}
	
	public Vita(String name) {
		super.setName(name);
		setExtension(name);
	}
	
	public Vita(String template, String name) {
		this(name);
		super.setTemplate(template);
	}
}
