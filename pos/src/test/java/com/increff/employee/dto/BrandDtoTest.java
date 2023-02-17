package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.util.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.*;

public class BrandDtoTest extends AbstractUnitTest {
    @Autowired
    private BrandDto brandDto;
    @Test
    public void testAdd() throws ApiException {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(" brand ");
        brandForm.setCategory("Category ");
        brandDto.add(brandForm);
        BrandData brandData = brandDto.getAll().get(0);
        assertEquals(1,brandDto.getAll().size());
        assertEquals("brand",brandData.getBrand());
        assertEquals(brandData.getCategory(),"category");
    }
//    @Test(expected = ApiException.class) when the expected output is an exception

}
