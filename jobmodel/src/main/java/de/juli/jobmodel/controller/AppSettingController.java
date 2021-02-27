package de.juli.jobmodel.controller;

import java.util.List;

import de.juli.jobmodel.model.AppSetting;

public class AppSettingController extends Controller {

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