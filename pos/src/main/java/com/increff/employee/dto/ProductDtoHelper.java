package com.increff.employee.dto;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

public class ProductDtoHelper {
    @Autowired
    private BrandService brandService;
    public BrandPojo brandAndCatFromId(int brandAndCatid) throws ApiException {
        return brandService.get(brandAndCatid);
    }

    public static ProductData convert(ProductPojo p,String brand,String category) {
        ProductData d = new ProductData();
        d.setBarcode(p.getBarcode());
        d.setBrand(brand);
        d.setCategory(category);
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        d.setId(p.getId());
        return d;
    }
    public static ProductPojo convert(ProductForm f,int brandAndCatId) {
        ProductPojo p = new ProductPojo();
        p.setBarcode(f.getBarcode());
        p.setBrand_category(brandAndCatId);
        p.setName(f.getName());
        p.setMrp(f.getMrp());
        return p;
    }
}
