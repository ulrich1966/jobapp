package de.juli.jobapp.jobmodel.model;

import javax.persistence.Entity;

@Entity
public class Letter extends Document{
	
	public Letter() {
		super();
	}
	
	public Letter(String name) {
		super(name);
		setExtension(name);
	}
	
	public Letter(String template, String name) {
		super(template, name);
	}
}
