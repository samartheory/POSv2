package com.increff.employee.service;

import com.increff.employee.dao.DaySalesDao;
import com.increff.employee.pojo.DaySalesPojo;
import com.increff.employee.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class DaySalesService {

    @Autowired
    private DaySalesDao daySalesDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(DaySalesPojo daySalesPojo) throws ApiException {
            daySalesDao.insert(daySalesPojo);
    }
    @Transactional(readOnly = true)
    public boolean presentWithDate(ZonedDateTime time){
        DaySalesPojo daySalesPojo = daySalesDao.selectWithDate(time);
        return Objects.isNull(daySalesPojo);
    }
//	@Transactional(readOnly = true)
//	public BrandPojo get(int id) throws ApiException {
//		return getCheck(id);
//	}

    @Transactional(readOnly = true)
    public List<DaySalesPojo> getAll() {
        return daySalesDao.selectAll();
    }
//
//	@Transactional(rollbackFor = ApiException.class)
//	public void update(int id, BrandPojo brandPojo) throws ApiException {
//		BrandPojo oldBrandPojo = getCheck(id);
//		oldBrandPojo.setBrand(brandPojo.getBrand());
//		oldBrandPojo.setCategory(brandPojo.getCategory());
//		brandDao.update(oldBrandPojo);
//	}
//
//	@Transactional(rollbackFor = ApiException.class)
//	public void delete(int id) {
//		brandDao.delete(id);
//	}
//
//	@Transactional(readOnly = true)
//	public BrandPojo getCheck(int id) throws ApiException {
//		BrandPojo brandPojo = brandDao.select(id);
//		if (brandPojo == null) {
//			throw new ApiException("Brand with given ID does not exit, id: " + id);
//		}
//		return brandPojo;
//	}
//
//	@Transactional(readOnly = true)
//	public BrandPojo get(String brand, String category) {
//		return brandDao.select(brand, category);
//
//	}

}
