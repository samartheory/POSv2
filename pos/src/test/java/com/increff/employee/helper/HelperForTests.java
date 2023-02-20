package com.increff.employee.helper;

import com.increff.employee.model.BrandForm;
import com.increff.employee.model.InventForm;
import com.increff.employee.model.ProductForm;

public class HelperForTests
{
    public static BrandForm createBrandForm(String brand, String category) {
        BrandForm brandForm = new BrandForm();
        brandForm.setBrand(brand);
        brandForm.setCategory(category);
        return brandForm;
    }
    public static InventForm createInventForm(int quantity, String barcode){
        InventForm inventForm = new InventForm();
        inventForm.setBarcode(barcode);
        inventForm.setQuantity(quantity);
        return inventForm;
    }
    public static ProductForm createProductForm(String brand, String category, String barcode, String name, double i){
        ProductForm productForm = new ProductForm();
        productForm.setBarcode(barcode);
        productForm.setBrand(brand);
        productForm.setCategory(category);
        productForm.setName(name);
        productForm.setMrp(i);
        return productForm;
    }
}
