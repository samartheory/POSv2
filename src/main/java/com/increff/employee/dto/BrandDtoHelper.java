package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;

import java.util.ArrayList;
import java.util.List;

public class BrandDtoHelper {
    public static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        d.setId(p.getId());
        return d;
    }

    public static BrandPojo convert(BrandForm f) {
        BrandPojo p = new BrandPojo();
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());
//        p.setId(f.getId());
        return p;
    }

    public static List<BrandData> convert(List<BrandPojo> list) {
        List<BrandData> list2 = new ArrayList<>();
        for (BrandPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }
}
