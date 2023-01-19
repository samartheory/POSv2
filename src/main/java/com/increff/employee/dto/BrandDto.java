package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandDto {
    @Autowired
    private BrandService brandService;

    public void add(BrandForm brandForm) throws ApiException {
        brandService.add(BrandDtoHelper.convert(brandForm));
    }

    public void delete(int id) {
        brandService.delete(id);
    }

    public BrandData get(int id) throws ApiException {
        return BrandDtoHelper.convert(brandService.get(id));
    }

    public List<BrandData> getAll() {
        return BrandDtoHelper.convert(brandService.getAll());
    }

    public void update( int id, BrandForm f) throws ApiException {
        BrandPojo p = BrandDtoHelper.convert(f);
        brandService.update(id, p);
    }

}
