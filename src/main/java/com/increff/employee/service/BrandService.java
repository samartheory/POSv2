package com.increff.employee.service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class BrandService {

	@Autowired
	private BrandDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(BrandPojo p) throws ApiException {
		if(StringUtil.isEmpty(p.getBrand()) || StringUtil.isEmpty(p.getCategory())) {
			throw new ApiException("brand or categories cannot be empty");
		}
		normalize(p);
		checkUnique(p);
		dao.insert(p);
	}

	@Transactional(readOnly = true)
	public void delete(int id) {
		dao.delete(id);
	}

	@Transactional(readOnly = true)
	public BrandPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional(readOnly = true)
	public List<BrandPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(int id, BrandPojo p) throws ApiException {
		if(StringUtil.isEmpty(p.getBrand()) || StringUtil.isEmpty(p.getCategory())) {
			throw new ApiException("brand or categories cannot be empty");
		}
		normalize(p);
		checkUnique(p);
		BrandPojo ex = getCheck(id);
		ex.setCategory(p.getCategory());
		ex.setBrand(p.getBrand());
		dao.update(ex);
	}
	public int getBrandAndCatId(String brand,String category) throws ApiException {
		BrandPojo brandPojo = dao.selectByBrandCategory(brand,category);
		if(Objects.isNull(brandPojo)){
			throw new ApiException("Brand or Category dosen't exist");
		}
		return brandPojo.getId();
	}
	@Transactional(readOnly = true)
	public BrandPojo getCheck(int id) throws ApiException {
		BrandPojo p = dao.select(id);
		if (Objects.isNull(p)) {
			throw new ApiException("Brand with given ID does not exit, id: " + id);
		}
		return p;
	}
	private void normalize(BrandPojo p) {
		p.setCategory(StringUtil.toLowerCase(p.getCategory()));
		p.setBrand(StringUtil.toLowerCase(p.getBrand()));
	}
	private void checkUnique(BrandPojo brandPojo) throws ApiException {
		if (!Objects.isNull(dao.selectByBrandCategory(brandPojo.getBrand(), brandPojo.getCategory()))) {
			throw new ApiException(brandPojo.getBrand() + " - " + brandPojo.getCategory() + " pair already exists");
		}
	}
}
