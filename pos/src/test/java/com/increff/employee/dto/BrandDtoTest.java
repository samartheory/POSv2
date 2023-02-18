package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.util.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.*;

public class BrandDtoTest extends AbstractUnitTest {
    @Autowired
    private BrandDto brandDto;
    @Test
    public void testAdd() throws ApiException {
        BrandForm brandForm = createBrandForm(" brand ","Category ");
        brandDto.add(brandForm);
        BrandData brandData = brandDto.getAll().get(0);
        assertEquals(1,brandDto.getAll().size());
        assertEquals("brand",brandData.getBrand());
        assertEquals(brandData.getCategory(),"category");
    }
    //    @Test(expected = ApiException.class) when the expected output is an exception
    //null add
    @Test(expected = ApiException.class)
    public void nullAdd() throws ApiException {
        brandDto.add(createBrandForm("",""));
    }
    //brand null
    @Test(expected = ApiException.class)
    public void brandNullAdd() throws ApiException {
        brandDto.add(createBrandForm("","category"));
    }
    //cateogry null
    @Test(expected = ApiException.class)
    public void categoryNullAdd() throws ApiException {
        brandDto.add(createBrandForm("brand",""));
    }
    //b and c already exists
    @Test(expected = ApiException.class)
    public void duplicateAdd() throws ApiException {
        brandDto.add(createBrandForm("brand","category"));
        brandDto.add(createBrandForm("brand","category"));
    }
    /*GET*/
    //get
    @Test
    public void testGet() throws ApiException {
        BrandForm brandForm = createBrandForm(" brand ","Category ");
        brandDto.add(brandForm);
        BrandData brandData = brandDto.getAll().get(0);
        BrandData brandData2 = brandDto.get(brandData.getId());
        assertEquals("brand",brandData2.getBrand());
        assertEquals("category",brandData2.getCategory());
    }
    //order id not exists
    @Test(expected = ApiException.class)
    public void ifOrderIdIsNotPresentGet() throws ApiException {
        BrandData brandData = brandDto.get(1);
    }
    /*get all*/
    //get
    @Test
    public void testGetAll() throws ApiException {
        BrandForm brandForm = createBrandForm("brand","category");
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAll();
        assertEquals(1,brandDataList.size());
        BrandForm brandForm2 = createBrandForm("brand2","category2");
        brandDto.add(brandForm2);
        brandDataList = brandDto.getAll();
        assertEquals(2,brandDataList.size());
    }
    //order id not exists

    /*update*/
    //update
    //id dosent exists
    private BrandForm createBrandForm(String brand, String category) {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(brand);
        brandForm.setCategory(category);
        return brandForm;
    }
}
