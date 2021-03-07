package de.juli.jobapp.jobmodel.controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class CriteriaController extends Controller {
	private static final long serialVersionUID = 1L;

	/**
	 * Findest duch den CriteriaController heraus, ob eine Tabelle eines
	 * bestimmten durch Class<T> clazz angegebenen Typs Daten saetze enthaelt
	 * und gibt 0 fuer keine, sonst die Anzal zuruek. So kann in der aufrufenden
	 * Methoden auf 0 abgefragt werden, statt auf null uns 0.
	 */
	public <T> Long getTableSize(Class<T> clazz) {
		Long result = 0l;
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(clazz)));
		result = em.createQuery(cq).getSingleResult();
		if(result != null) {
			return result;
		}
		return result;
	}
}
