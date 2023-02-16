package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.service.AbstractUnitTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class BrandDtoTest extends AbstractUnitTest {
    @Autowired
    private BrandDto brandDto;
    @Test
    public void testAdd(){
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(" brand ");
        brandForm.setCategory("Category ");
        BrandData brandData = brandDto.getAll().get(0);
        assertEquals(brandData.getBrand(),"brand");
        assertEquals(brandData.getCategory(),"category");
    }
}
