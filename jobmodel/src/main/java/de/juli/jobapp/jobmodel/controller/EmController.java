package de.juli.jobapp.jobmodel.controller;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EmController {
	 private static EmController controller;
	 private static final String PERSISTENCE_UNIT_NAME = "pu";
	 private static EntityManagerFactory emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
	 private static  EntityManager em = emf.createEntityManager();

	 private EmController(){
		 if(controller == null) {
			 controller = new EmController();
		 }
	 }

	 public static EntityManager getEm() {
		 if(em != null && em.isOpen()) {
			 return em;
		 } else {
			 return null;
		 }
	 }

	 public static void close() {
		 if(em != null && em.isOpen()) {
			 em.close();
		 }
	 }
	 
	 public static String getDb() {
		 String user = (String) emf.getProperties().get("hibernate.connection.user");
		 return user;
	 }

	 public static String getDbUser() {
		 String url = (String) emf.getProperties().get("hibernate.connection.url");
		 String[] split = url.split("/");
		 return split[split.length-1];
		 
	 }
}
