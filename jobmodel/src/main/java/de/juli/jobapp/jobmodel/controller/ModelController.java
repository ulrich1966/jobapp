package de.juli.jobapp.jobmodel.controller;

import de.juli.jobapp.jobmodel.model.Model;

public class ModelController extends Controller {
	private static final long serialVersionUID = 1L;
	
	public <T> Model create(Model model) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(model);
		em.getTransaction().commit();
		return model;
	}

}
