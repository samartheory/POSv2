package com.increff.employee.dao;

import com.increff.employee.pojo.InventPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class InventDao extends AbstractDao {

	private static final String DELETE_ID = "delete from InventPojo p where id=:id";
	private static final String SELECT_ID = "select p from InventPojo p where id=:id";
	private static final String SELECT_ALL = "select p from InventPojo p";
	private static final String SELECT_BY_BRAND_CAT = "select p from InventPojo p where brand = : brand and category = : category";

	@PersistenceContext
	private EntityManager em;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(InventPojo p) {
			em.persist(p);
	}
	@Transactional(readOnly = true)
	public InventPojo selectByBrandCategory(String brand, String category) {
		TypedQuery<InventPojo> query = em().createQuery(SELECT_BY_BRAND_CAT, InventPojo.class);
		query.setParameter("brand", brand);
		query.setParameter("category", category);
		return getSingle(query);
	}
	@Transactional(rollbackFor = ApiException.class)
	public int delete(int id) {
		Query query = em.createQuery(DELETE_ID);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
@Transactional(readOnly = true)
	public InventPojo select(int id) {
		TypedQuery<InventPojo> query = getQuery(SELECT_ID, InventPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
@Transactional(readOnly = true)
	public List<InventPojo> selectAll() {
		TypedQuery<InventPojo> query = getQuery(SELECT_ALL, InventPojo.class);
		return query.getResultList();
	}
@Transactional(rollbackFor = ApiException.class)
	public void update(InventPojo p) {
	}


}
