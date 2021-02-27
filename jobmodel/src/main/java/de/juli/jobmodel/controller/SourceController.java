package de.juli.jobmodel.controller;

import java.util.List;

import de.juli.jobmodel.model.Source;

public class SourceController extends Controller {

	public Source create(Source model){
		return persist(model);
	}

	public Source update(Source model) {
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		return model;		
	}

	public Source persist(Source model){
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		return model;
	}

	public Source findById(Long id) {
		Source result = em.find(Source.class, id);
		return result;
	}

	public Source findByName(String name) {
		Source result = em.createNamedQuery("Source.findByName", Source.class).setParameter("name", name).getSingleResult();
		return result;
	}

	public List<Source> findAll() {
		return em.createNamedQuery("Source.findAll", Source.class).getResultList();
	}

	public void remove(Source model) {
		em.getTransaction().begin();
		em.remove(model);
		em.getTransaction().commit();
	}

	public Source findFirst() {
		return findAll().get(0);
	}
}
