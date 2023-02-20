package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.util.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.increff.employee.helper.HelperForTests;
import static org.junit.Assert.*;

public class BrandDtoTest extends AbstractUnitTest {
    @Autowired
    private BrandDto brandDto;
    @Test
    public void testAdd() throws ApiException {
        BrandForm brandForm = HelperForTests.createBrandForm(" brand ","Category ");
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
        brandDto.add(HelperForTests.createBrandForm("",""));
    }
    //brand null
    @Test(expected = ApiException.class)
    public void brandNullAdd() throws ApiException {
        brandDto.add(HelperForTests.createBrandForm("","category"));
    }
    //cateogry null
    @Test(expected = ApiException.class)
    public void categoryNullAdd() throws ApiException {
        brandDto.add(HelperForTests.createBrandForm("brand",""));
    }
    //b and c already exists
    @Test(expected = ApiException.class)
    public void duplicateAdd() throws ApiException {
        brandDto.add(HelperForTests.createBrandForm("brand","category"));
        brandDto.add(HelperForTests.createBrandForm("brand","category"));
    }
    /*GET*/
    //get
    @Test
    public void testGet() throws ApiException {
        BrandForm brandForm = HelperForTests.createBrandForm(" brand ","Category ");
        brandDto.add(brandForm);
        BrandData brandData = brandDto.getAll().get(0);
        BrandData brandData2 = brandDto.get(brandData.getId());
        assertEquals("brand",brandData2.getBrand());
        assertEquals("category",brandData2.getCategory());
    }
    //order id not exists
    @Test(expected = ApiException.class)
    public void ifBrandIdIsNotPresentGet() throws ApiException {
        BrandData brandData = brandDto.get(1);
    }
    /*get all*/
    //get
    @Test
    public void testGetAll() throws ApiException {
        BrandForm brandForm = HelperForTests.createBrandForm("brand","category");
        brandDto.add(brandForm);
        List<BrandData> brandDataList = brandDto.getAll();
        assertEquals(1,brandDataList.size());
        BrandForm brandForm2 = HelperForTests.createBrandForm("brand2","category2");
        brandDto.add(brandForm2);
        brandDataList = brandDto.getAll();
        assertEquals(2,brandDataList.size());
    }
    //order id not exists
    @Test
    public void ifNoOrdersArePresentGetAll(){
        List<BrandData> brandData = brandDto.getAll();
        assertEquals(0,brandData.size());
    }
    /*update*/
    //update
    @Test
    public void updateTest() throws ApiException {
        BrandForm brandForm = HelperForTests.createBrandForm("brand","category");
        brandDto.add(brandForm);
        BrandData brandData = brandDto.getAll().get(0);
        BrandForm newBrandForm = HelperForTests.createBrandForm("new","newc");
        brandDto.update(brandData.getId(),newBrandForm);
        brandData = brandDto.getAll().get(0);
        assertEquals("new",brandData.getBrand());
        assertEquals("newc",brandData.getCategory());
    }
    //id dosent exists
    @Test(expected = ApiException.class)
    public void updateIdNotPresent() throws ApiException {
        BrandForm brandForm = HelperForTests.createBrandForm("b","c");
        brandDto.update(0,brandForm);
    }

}
