package com.increff.employee.dto;

import com.increff.employee.model.InventData;
import com.increff.employee.model.InventForm;
import com.increff.employee.pojo.InventPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class InventDtoHelper {


    public static InventData convert(InventPojo p,String barcode) {
        InventData d = new InventData();
        d.setQuantity(p.getQuantity());
        d.setId(p.getId());
        d.setBarcode(barcode);
        return d;
    }

    public static InventPojo convert(InventForm f,int id) {
        InventPojo p = new InventPojo();
        p.setQuantity(f.getQuantity());
//        p.setBarcode(f.getBarcode());//barcode use
        p.setId(id);
        return p;
    }
}
