package com.increff.employee.dao;

import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public class ProductDao extends AbstractDao {

	private static final String DELETE_ID = "delete from ProductPojo p where id=:id";
	private static final String SELECT_ID = "select p from ProductPojo p where id=:id";
	private static final String SELECT_ALL = "select p from ProductPojo p";
	private static final String SELECT_BY_BARCODE = "select p from ProductPojo p where barcode = : barcode";

	@PersistenceContext
	private EntityManager em;

	@Transactional(rollbackFor = ApiException.class)
	public void insert(ProductPojo p) {
		em.persist(p);
	}
	@Transactional(rollbackFor = ApiException.class)
	public int delete(int id) {
		Query query = em.createQuery(DELETE_ID);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
	@Transactional(readOnly = true)
	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery(SELECT_ID, ProductPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	@Transactional(readOnly = true)
	public ProductPojo selectByBarcode(String barcode){
		TypedQuery<ProductPojo> query = getQuery(SELECT_BY_BARCODE, ProductPojo.class);
		query.setParameter("barcode",barcode);
		return getSingle(query);
	}
	@Transactional(readOnly = true)
	public List<ProductPojo> selectAll() {
		TypedQuery<ProductPojo> query = getQuery(SELECT_ALL, ProductPojo.class);
		return query.getResultList();
	}
	@Transactional(rollbackFor = ApiException.class)
	public void update(ProductPojo p) {
	}
}
