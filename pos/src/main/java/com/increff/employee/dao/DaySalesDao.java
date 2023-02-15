package com.increff.employee.dao;

import com.increff.employee.pojo.DaySalesPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public class DaySalesDao extends AbstractDao {

	private static final String SELECT_ALL = "select p from DaySalesPojo p";
	private static final String SELECT_WITH_DATE = "select p from DaySalesPojo p where date=:date";


	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(DaySalesPojo daySalesPojo) {
		entityManager.persist(daySalesPojo);
	}

	@Transactional(readOnly = true)
	public List<DaySalesPojo> selectAll() {
		TypedQuery<DaySalesPojo> query = getQuery(SELECT_ALL, DaySalesPojo.class);
		return query.getResultList();
	}
	@Transactional(readOnly = true)
	public DaySalesPojo selectWithDate(ZonedDateTime time){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String date = time.format(formatter);
		date += " 00:00:00";
		TypedQuery<DaySalesPojo> query = getQuery(SELECT_WITH_DATE, DaySalesPojo.class);
		query.setParameter("date",time);
		return query.getSingleResult();
	}
}
