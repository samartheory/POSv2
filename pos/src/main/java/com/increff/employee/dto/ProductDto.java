package com.increff.employee.dto;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.increff.employee.dto.ProductDtoHelper.convert;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductDto {
    @Autowired
    private ProductService productService;
    @Autowired
    private BrandService brandService;
    public void add(ProductForm form) throws ApiException {
        int brandAndCatId = brandService.getBrandAndCatId(StringUtil.toLowerCase(form.getBrand()), StringUtil.toLowerCase(form.getCategory()));
        ProductPojo p = convert(form,brandAndCatId);
        // Validate if brandpojo exists
        productService.add(p);
    }

    public void delete(int id) {
        productService.delete(id);
    }

    public ProductData get(int id) throws ApiException {
        ProductPojo productPojo = productService.get(id);
        BrandPojo brandPojo = brandService.get(productPojo.getBrand_category());
        return convert(productPojo,brandPojo.getBrand(),brandPojo.getCategory());
    }
    public List<ProductData> getAll() throws ApiException {
        List<ProductPojo> list = productService.getAll();
        return getAllConverter(list);
    }
    public List<ProductData> getAllConverter(List<ProductPojo> list) throws ApiException {
        List<ProductData> list2 = new ArrayList<ProductData>();
        for (ProductPojo p : list) {
            BrandPojo brandPojo = brandService.get(p.getBrand_category());
            list2.add(convert(p,brandPojo.getBrand(),brandPojo.getCategory()));
        }
        return list2;
    }
    public void update( int id, ProductForm f) throws ApiException {
        int brandAndCatId = brandService.getBrandAndCatId(StringUtil.toLowerCase(f.getBrand()), StringUtil.toLowerCase(f.getCategory()));
        productService.update(id, convert(f,brandAndCatId));
    }

}
