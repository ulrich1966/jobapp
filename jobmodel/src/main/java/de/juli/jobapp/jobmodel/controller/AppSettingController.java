package de.juli.jobapp.jobmodel.controller;

import java.util.List;

import de.juli.jobapp.jobmodel.model.AppSetting;
import de.juli.jobapp.jobmodel.model.Model;

public class AppSettingController extends Controller {
	private static final long serialVersionUID = 1L;

	public AppSetting create(AppSetting model){
		return persist(model);
	}

	public AppSetting persist(AppSetting model){
		em.getTransaction().begin();
		em.persist(model);
		em.getTransaction().commit();
		return model;
	}
	
	public AppSetting findById(Long id) {
		AppSetting result = em.find(AppSetting.class, id);
		return result;
	}

	public List<AppSetting> findAll() {
		return em.createNamedQuery("AppSetting.findAll", AppSetting.class).getResultList();
	}

	public void remove(AppSetting model) {
		em.getTransaction().begin();
		em.remove(model);
		em.getTransaction().commit();
	}

	public AppSetting findFirst() {
		AppSetting model = null;
		model = em.createNamedQuery("AppSetting.findAll", AppSetting.class).getSingleResult();
		return model;
	}

}