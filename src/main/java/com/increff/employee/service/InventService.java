package com.increff.employee.service;

import com.increff.employee.dao.InventDao;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.InventPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class InventService {

	@Autowired
	private InventDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(InventPojo p) throws ApiException {
		if(p.getQuantity() == 0) {
			throw new ApiException("quantity cannot be empty");
		}
		dao.insert(p);
	}
	//TODO: if quantity turns 0 then delete that entry automatically
	//TODO: if we add an already existing product into the inventory than it adds new and old quantity
	@Transactional(readOnly = true)
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
	public void update(int id, InventPojo p) throws ApiException {
		InventPojo ex = getCheck(id);
		ex.setQuantity(p.getQuantity());
		dao.update(ex);
	}

	@Transactional(readOnly = true)
	public InventPojo getCheck(int id) throws ApiException {
		InventPojo p = dao.select(id);
		if (Objects.isNull(p)) {
			throw new ApiException("Invent with given ID does not exit, id: " + id);
		}
		return p;
	}


}
