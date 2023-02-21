package com.increff.employee.dto;

import com.increff.employee.model.InventData;
import com.increff.employee.model.InventForm;
import com.increff.employee.pojo.InventPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.util.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import static com.increff.employee.dto.helper.InventDtoHelper.convert;

import java.util.ArrayList;
import java.util.List;
@Service
public class InventDto {
    @Autowired
    private InventoryService service;
    @Autowired
    private ProductService productService;
//product service
    public void add(InventForm form) throws ApiException {
        form.setBarcode(StringUtil.toLowerCase(form.getBarcode()));
        ProductPojo productPojo = productService.getIdByBarcode(form.getBarcode());
        if(service.ifBarcodePresent(productPojo.getId())){
            throw new ApiException("Barcode already present");
        }
        InventPojo p = convert(form,productPojo.getId());
        service.add(p);
    }

    public void delete(int id) {
        service.delete(id);
    }

    public InventData get(int id) throws ApiException {
        ProductPojo productPojo = productService.get(id);
        InventPojo p = service.get(id);
        return convert(p,productPojo.getBarcode());
    }

    public List<InventData> getAll() throws ApiException {
        List<InventPojo> list = service.getAll();
        return getAllConverter(list);
    }
    public List<InventData> getAllConverter(List<InventPojo> list) throws ApiException {
        List<InventData> list2 = new ArrayList<InventData>();
        for (InventPojo p : list) {
            ProductPojo productPojo = productService.get(p.getId());
            list2.add(convert(p,productPojo.getBarcode()));
        }
        return list2;
    }
    public void update(int id,int newQuantity) throws ApiException {
        if(newQuantity < 0){
            throw new ApiException("Invalid Quantity");
        }
        service.update(id,newQuantity);
    }

}
