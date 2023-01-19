package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao {

	private static final String DELETE_ID = "delete from OrderPojo p where id=:id";
	private static final String SELECT_ID = "select p from OrderPojo p where id=:id";
	private static final String SELECT_ALL = "select p from OrderPojo p";

	@PersistenceContext
	private EntityManager em;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(OrderPojo p) {
		em.persist(p);
	}
	@Transactional(rollbackFor = ApiException.class)
	public int delete(int id) {
		Query query = em.createQuery(DELETE_ID);
		query.setParameter("id", id);
		return query.executeUpdate();
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
		System.out.println("tillthis");
		return query.getResultList();


	}
	@Transactional(rollbackFor = ApiException.class)
	public void update(OrderPojo p) {
	}



}
