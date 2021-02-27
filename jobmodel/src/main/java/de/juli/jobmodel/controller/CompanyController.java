package de.juli.jobmodel.controller;

import java.util.List;

import de.juli.jobmodel.model.Company;
import de.juli.jobmodel.model.Job;
import de.juli.jobmodel.model.Model;

public class CompanyController extends Controller {

	public Company create(Company model){
		return persist(model);
	}

	public Company update(Company model) {
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		return model;		
	}

	public Company persist(Company model){
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		return model;
	}

	public Company findByName(String name) {
		Company result = em.createNamedQuery("Company.findByName", Company.class).setParameter("name", name).getSingleResult();
		return result;
	}

	public List<Company> findAll() {
		return em.createNamedQuery("Company.findAll", Company.class).getResultList();
	}

	public Company findFirst() {
		return findAll().get(0);
	}

	public boolean remove(Company model) {
		boolean success = false;
		JobController jc = new JobController();
		try {
			if(!em.contains(model)){
				em.merge(model);
			}
			em.getTransaction().begin();
			model.getJobs().forEach(e -> jc.delete(e));
			remove(model.getContact());
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
