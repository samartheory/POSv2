package com.increff.employee.dao;

import com.increff.employee.service.ApiException;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Repository;
import com.increff.employee.pojo.BrandPojo;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class BrandDao extends AbstractDao {

	private static final String DELETE_ID = "delete from BrandPojo p where id=:id";
	private static final String SELECT_ID = "select p from BrandPojo p where id=:id";
	private static final String SELECT_ALL = "select p from BrandPojo p";
	private static final String SELECT_BY_BRAND_CAT = "select p from BrandPojo p where brand = : brand and category = : category";
//	private static final String SELECT_BY_CAT = "select p from BrandPojo p where category=:category";
	@PersistenceContext
	private EntityManager em;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(BrandPojo p) {
			em.persist(p);
//			TODO: configure log4j
	}
	@Transactional(readOnly = true)
	public BrandPojo selectByBrandCategory(String brand, String category) {
		TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_BRAND_CAT, BrandPojo.class);
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
//	public BrandPojo selectByCat(String category) {
//		TypedQuery<BrandPojo> query = em().createQuery(SELECT_BY_CAT, BrandPojo.class);
//		query.setParameter("category", category);
//		return getSingle(query);
//	}
	@Transactional(readOnly = true)
	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ID, BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
@Transactional(readOnly = true)
	public List<BrandPojo> selectAll() {
		TypedQuery<BrandPojo> query = getQuery(SELECT_ALL, BrandPojo.class);
		return query.getResultList();
	}
	@Transactional(rollbackFor = ApiException.class)
	public void update(BrandPojo p) {
	}


}
