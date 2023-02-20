package com.increff.employee.dto;

import com.increff.employee.model.BrandForm;
import com.increff.employee.model.InventData;
import com.increff.employee.model.InventForm;
import com.increff.employee.model.ProductForm;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.util.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.increff.employee.helper.HelperForTests;
import static org.junit.Assert.*;

public class InventDtoTest extends AbstractUnitTest {
    @Autowired
    private InventDto inventDto;
    @Autowired
    private ProductDto productDto;
    @Autowired
    private BrandDto brandDto;
    @Test
    public void testAdd() throws ApiException {
        BrandForm brandForm = HelperForTests.createBrandForm("brand","category");
        brandDto.add(brandForm);
        ProductForm productForm = HelperForTests.createProductForm(brandForm.getBrand(),brandForm.getCategory(),"barcode","name",1);
        productDto.add(productForm);
        InventForm inventForm = HelperForTests.createInventForm(3,"barcode");
        inventDto.add(inventForm);
        InventData inventData = inventDto.get(1);
        assertEquals("barcode",inventData.getBarcode());
        assertEquals(3,inventData.getQuantity());
    }
    //barcode dosent exists
    @Test(expected = ApiException.class)
    public void testAddBarcodeNull() throws ApiException {
        BrandForm brandForm = HelperForTests.createBrandForm("brand","category");
        brandDto.add(brandForm);
        ProductForm productForm = HelperForTests.createProductForm(brandForm.getBrand(),brandForm.getCategory(),"barcode","name",1);
        InventForm inventForm = HelperForTests.createInventForm(3,"barcode2");
        inventDto.add(inventForm);
    }
    //invalid quan

    //get
    //get barcode dosent exists
    //getAll
    //update
    //update invalid quan
}
