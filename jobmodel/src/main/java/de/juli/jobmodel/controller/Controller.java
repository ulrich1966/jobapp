package de.juli.jobmodel.controller;

import javax.persistence.EntityManager;

public abstract class Controller {
	protected EntityManager em;

	public Controller() {
		em = EmController.getEm();
	}
}
