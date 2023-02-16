package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao {

	private static final String DELETE_ID = "delete from OrderPojo p where id=:id";
	private static final String SELECT_ID = "select p from OrderPojo p where id=:id";
	private static final String SELECT_ALL = "select p from OrderPojo p";
	private static final String SELECT_ALL_ORDER_DATE = "select p from OrderPojo p where time >= :startDate and time <= :endDate and status = true";


	@PersistenceContext
	private EntityManager em;

	@Transactional
	public void insert(OrderPojo p) {
		em.persist(p);
	}
	@Transactional
	public int delete(int id) {
		Query query = em.createQuery(DELETE_ID);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
	@Transactional(readOnly = true)
	public List<OrderPojo> selectBetweeenDates(ZonedDateTime startDate, ZonedDateTime endDate) {
		//select orders between two dates
		TypedQuery<OrderPojo> query = getQuery(SELECT_ALL_ORDER_DATE, OrderPojo.class);
		return query.setParameter("startDate", startDate).setParameter("endDate", endDate).getResultList();
	}
@Transactional(readOnly = true)
	public OrderPojo select(int id) {
		TypedQuery<OrderPojo> query = getQuery(SELECT_ID, OrderPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	@Transactional(readOnly = true)
	public List<OrderPojo> selectAll() {

		TypedQuery<OrderPojo> query = getQuery(SELECT_ALL, OrderPojo.class);
		return query.getResultList();


	}
	@Transactional
	public void update(OrderPojo p) {
	}



}
