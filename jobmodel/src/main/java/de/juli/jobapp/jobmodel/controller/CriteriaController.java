package de.juli.jobapp.jobmodel.controller;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

public class CriteriaController<T> extends Controller {
	private static final long serialVersionUID = 1L;

	public Long getTableSize(Class<T> clazz) {
		CriteriaBuilder qb = em.getCriteriaBuilder();
		CriteriaQuery<Long> cq = qb.createQuery(Long.class);
		cq.select(qb.count(cq.from(clazz)));
		//ParameterExpression<Integer> p = qb.parameter(Integer.class);
		return em.createQuery(cq).getSingleResult();
	}
}
