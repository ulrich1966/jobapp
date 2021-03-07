package de.juli.jobapp.jobmodel.controller;

import java.util.List;

import de.juli.jobapp.jobmodel.model.Model;

public class PersistenceController<T> extends Controller implements ControllerInterf<T> {
	private static final long serialVersionUID = 1L;
	
	@Override
	public Model create(Model model) {
		if (!em.getTransaction().isActive()) {
			em.getTransaction().begin();
		}
		em.persist(model);
		em.getTransaction().commit();
		return model;
	}

	@Override
	public Model persist(Model model) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Model findByName(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Model> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Model model) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Model findFirst() {
		// TODO Auto-generated method stub
		return null;
	}
}
