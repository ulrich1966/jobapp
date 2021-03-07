package de.juli.jobapp.jobmodel.controller;

import java.io.Serializable;

import javax.persistence.EntityManager;

public abstract class Controller implements Serializable {
	private static final long serialVersionUID = 1L;
	protected EntityManager em;

	public Controller() {
		em = EmController.getEm();
	}

}
