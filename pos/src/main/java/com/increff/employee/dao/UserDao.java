package com.increff.employee.dao;

import java.util.List;

import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.increff.employee.service.ApiException;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.UserPojo;

@Repository
public class UserDao extends AbstractDao {

	private static final String DELETE_ID = "delete from UserPojo p where id=:id";
	private static final String SELECT_ID = "select p from UserPojo p where id=:id";
	private static final String SELECT_EMAIL = "select p from UserPojo p where email=:email";
	private static final String SELECT_ALL = "select p from UserPojo p";


	@Transactional(rollbackFor = ApiException.class)
	public void insert(UserPojo p) {
		em().persist(p);
	}
	@Transactional(rollbackFor = ApiException.class)
	public int delete(int id) {
		Query query = em().createQuery(DELETE_ID);
		query.setParameter("id", id);
		return query.executeUpdate();
	}
	@Transactional(readOnly = true)
	public UserPojo select(int id) {
		TypedQuery<UserPojo> query = getQuery(SELECT_ID, UserPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	@Transactional(readOnly = true)
	public UserPojo select(String email) {
		TypedQuery<UserPojo> query = getQuery(SELECT_EMAIL, UserPojo.class);
		query.setParameter("email", email);
		return getSingle(query);
	}
	@Transactional(readOnly = true)
	public List<UserPojo> selectAll() {
		TypedQuery<UserPojo> query = getQuery(SELECT_ALL, UserPojo.class);
		return query.getResultList();
	}
	@Transactional(rollbackFor = ApiException.class)
	public void update(UserPojo p) {
	}


}
