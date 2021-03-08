package de.juli.jobapp.jobmodel.controller;

import java.util.List;

import de.juli.jobapp.jobmodel.model.Company;
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
	
	@SuppressWarnings("unchecked")
	public <T> T persist(Model model){
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		return (T) model;
	}

	public <T> Model update(Model model) {
		if(!em.contains(model)) {
			em.merge(model);
		}
		return persist(model);
	}

	public <T> T findByName(Class<T> clazz, String name) {
		String queryName = String.format("%s.findByName", clazz.getSimpleName());
		return em.createNamedQuery(queryName, clazz).setParameter("name", name).getSingleResult();
	}
	
	public <T> List<T> findAll(Class<T> clazz) {
		String queryName = String.format("%s.findAll", clazz.getSimpleName());
		return em.createNamedQuery(queryName, clazz).getResultList();
	}

	public <T> T findFirst(Class<T> clazz) {
		String queryName = String.format("%s.findAll", clazz.getSimpleName());
		return em.createNamedQuery(queryName, clazz).getResultList().get(0);
	}

	public void remove(Model model) {
		if(model != null) {
			em.remove(model);
		}	
	}
	
	//TODO Dasa Loeschen muss ich mir noch mal ansehen!
	public boolean remove(Company model) {
		boolean success = false;
		try {
			if(!em.contains(model)){
				em.merge(model);
			}
			em.getTransaction().begin();
			model.getJobs().forEach(e -> remove(e));
			remove(model.getContact());
			remove(model);
			em.getTransaction().commit();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}



}
