package de.juli.jobmodel.controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.juli.jobmodel.model.Job;
import de.juli.jobmodel.model.Model;

public class JobController extends Controller{
	private static final long serialVersionUID = 1L;
	private static final Logger LOG = LoggerFactory.getLogger(JobController.class);
	
	public Job create(Job model){
		if(!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(model);
		em.getTransaction().commit();
		return model;
	}

	public Job persist(Job model){
		return create(model);
	}

	public Job update(Job model) {
		if(!em.contains(model)) {
			em.merge(model);
		}
		return persist(model);
	}

	public Job findById(Long id) {
		return em.find(Job.class, id);
	}
	
	public Job findFirst() {
		return em.createNamedQuery("Job.findAll", Job.class).getSingleResult();
	}

	public List<Job> findAll() {
		try {
			return em.createNamedQuery("Job.findAll", Job.class).getResultList();			
		} catch(EntityNotFoundException e) {		
			LOG.error(e.getMessage());
		}
		return null;
	}

	public boolean delete(Job model) {
		boolean success = false;
		try {
			if(!em.contains(model)){
				em.merge(model);
			}
			em.getTransaction().begin();
			model.getStates().forEach(e -> remove(e));
			model.getHistorys().forEach(e -> remove(e));
			remove(model.getLetter());
			remove(model.getVita());
			remove(model.getEmail());
			remove(model.getPdf());
			remove(model);
			em.getTransaction().commit();
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return success;
	}

	private void remove(Model model) {
		if(model != null) {
			em.remove(model);
		}
		
	}


}
