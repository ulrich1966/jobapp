package de.juli.jobapp.jobmodel.controller;

import java.util.List;

import de.juli.jobapp.jobmodel.model.Model;

public interface ControllerInterf<T> {
	public abstract Model create(Model model);
	public abstract Model persist(Model model);
	public abstract Model findById(Long id); 
	public abstract Model findByName(String name); 
	public abstract <T> List<T> findAll(); 
	public void remove(Model model); 
	public abstract Model findFirst();
}