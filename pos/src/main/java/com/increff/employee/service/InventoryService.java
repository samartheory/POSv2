package com.increff.employee.service;

import com.increff.employee.dao.InventDao;
import com.increff.employee.pojo.InventPojo;
import com.increff.employee.util.ApiException;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class InventoryService {

	@Autowired
	private InventDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(InventPojo p) throws ApiException {
		if(p.getQuantity() < 0) {
			throw new ApiException("Quantity cannot be negative");
		}
		dao.insert(p);
	}
	@Transactional
	public void delete(int id) {
		dao.delete(id);
	}

	@Transactional(rollbackFor = ApiException.class)
	public InventPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional(readOnly = true)
	public List<InventPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(int id,int newQuantity) throws ApiException {
		InventPojo ex = getCheck(id);
		ex.setQuantity(newQuantity);
		dao.update(ex);
	}
	@Transactional(rollbackFor = ApiException.class)//todo remove 1 update fucntion
	public void updateQuantity(int id,int newQuantity) throws ApiException {
		InventPojo inventPojo = get(id);
		inventPojo.setQuantity(newQuantity);
	}
	@Transactional(readOnly = true)
	public InventPojo getCheck(int id) throws ApiException {
		InventPojo p = dao.select(id);
		if (Objects.isNull(p)) {
			throw new ApiException("Invent with given ID does not exit, id: " + id);
		}
		return p;
	}
	public boolean ifBarcodePresent(int id){
		InventPojo p = dao.select(id);
		return !Objects.isNull(p);
	}

}
