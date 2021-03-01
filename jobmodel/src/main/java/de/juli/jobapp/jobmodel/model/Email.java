package de.juli.jobapp.jobmodel.model;

import javax.persistence.Entity;

@Entity
public class Email extends Document {
	
	public Email() {
		super();
	}
	
	public Email(String name) {
		super.setName(name);
		setExtension(name);
	}
	
	public Email(String template, String name) {
		this(name);
		super.setTemplate(template);
	}
}
