package de.juli.jobmodel.controller;

import java.util.List;

import de.juli.jobmodel.model.Contact;

public class ContactController extends Controller {
	private static final long serialVersionUID = 1L;

	public Contact create(Contact model){
		return persist(model);
	}

	public Contact persist(Contact model){
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		return model;
	}

	public Contact findByName(String name) {
		Contact result = em.createNamedQuery("Contact.findByName", Contact.class).setParameter("name", name).getSingleResult();
		return result;
	}

	public List<Contact> findAll() {
		return em.createNamedQuery("Contact.findAll", Contact.class).getResultList();
	}

	public void remove(Contact model) {
		em.getTransaction().begin();
		em.remove(model);
		em.getTransaction().commit();
	}

	public Contact findFirst() {
		return findAll().get(0);
	}
}
