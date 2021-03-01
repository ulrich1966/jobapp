package de.juli.jobapp.jobmodel.controller;

import java.util.List;

import de.juli.jobapp.jobmodel.model.Account;

public class AccountController extends Controller {
	private static final long serialVersionUID = 1L;

	public Account create(Account model){
		return persist(model);
	}

	public Account persist(Account model){
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		return model;
	}
	
	public Account findById(Long id) {
		Account result = em.find(Account.class, id);
		return result;
	}

	public Account findByName(String name) {
		Account result = em.createNamedQuery("Account.findByName", Account.class).setParameter("name", name).getSingleResult();
		return result;
	}

	public List<Account> findAll() {
		return em.createNamedQuery("Account.findAll", Account.class).getResultList();
	}

	public void remove(Account model) {
		em.getTransaction().begin();
		em.remove(model);
		em.getTransaction().commit();
	}

	public Account findFirst() {
		return findAll().get(0);
	}

}