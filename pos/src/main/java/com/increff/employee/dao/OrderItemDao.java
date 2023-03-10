package com.increff.employee.dao;

import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class OrderItemDao extends AbstractDao {

	private static final String DELETE_ID = "delete from OrderItemPojo p where id=:id";
	private static final String SELECT_ID = "select p from OrderItemPojo p where id=:id";
	private static final String SELECT_ALL = "select p from OrderItemPojo p";
	private static final String SELECT_BY_PRODUCT_ID = "select p from OrderItemPojo p where productId=:id";
	private static final String SELECT_ALL_WITH_ORDER_ID = "select p from OrderItemPojo p where orderId=:id";

	@Transactional
	public void insert(OrderItemPojo p) {
		em.persist(p);
	}
    @Transactional
	public int delete(int id) {
		Query query = em.createQuery(DELETE_ID);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
	@Transactional(readOnly = true)
	public OrderItemPojo select(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(SELECT_ID, OrderItemPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	@Transactional(readOnly = true)
	public List<OrderItemPojo> selectAll() {
		TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL, OrderItemPojo.class);
		return query.getResultList();
	}
	@Transactional(readOnly = true)
	public List<OrderItemPojo> selectAllWithOrderId(int id) {
		TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL_WITH_ORDER_ID, OrderItemPojo.class);
		query.setParameter("id",id);
		return query.getResultList();
	}
	@Transactional
	public void update(OrderItemPojo p) {

	}



}
