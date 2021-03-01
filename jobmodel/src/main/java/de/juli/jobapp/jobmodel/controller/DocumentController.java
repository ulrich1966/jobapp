package de.juli.jobapp.jobmodel.controller;

import java.util.List;

import de.juli.jobapp.jobmodel.model.Document;
import de.juli.jobapp.jobmodel.model.Model;

public class DocumentController extends Controller {
	private static final long serialVersionUID = 1L;

	public Model create(Document model){
		return persist(model);
	}

	public Model persist(Model model){
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		return model;
	}
	
	public Document findById(Long id) {
		Document result = em.find(Document.class, id);
		return result;
	}

//	public Document findByName(String name) {
//		Document result = em.createNamedQuery("Document.findByName", Document.class).setParameter("name", name).getSingleResult();
//		return result;
//	}
//
//	public List<Document> findAll() {
//		return em.createNamedQuery("Document.findAll", Document.class).getResultList();
//	}

	public void remove(Model model) {
		em.getTransaction().begin();
		em.remove(model);
		em.getTransaction().commit();
	}

//	public Document findFirst() {
//		return findAll().get(0);
//	}

	public List<Document> findByType(String type) {
		List<Document> results = em.createNamedQuery("Document.findByType", Document.class).setParameter("type", type).getResultList();
		return results;
	}

}