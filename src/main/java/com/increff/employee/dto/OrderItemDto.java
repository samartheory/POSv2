package com.increff.employee.dto;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.dto.OrderItemDtoHelper.*;

@Service
public class OrderItemDto {
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InventService inventService;
    public void add(OrderItemForm orderItemForm) throws ApiException {

        if(StringUtil.isEmpty(orderItemForm.getBarcode())){
            throw new ApiException("Barcode cannot be empty");
        }
        if(orderItemForm.getQuantity() <= 0){
            throw new ApiException("Quantity should be a natural number");
        }
        ProductPojo productPojo = productService.getIdByBarcode(orderItemForm.getBarcode());
        int availableQuantity = inventService.get(productPojo.getId()).getQuantity();
        if(availableQuantity < orderItemForm.getQuantity()){
            throw new ApiException("Not enough items. Available Items = " + availableQuantity);
        }
        orderItemService.add(productToOrderItem(productPojo,orderItemForm));
    }

    public void delete(int id) {
        orderItemService.delete(id);
    }

    public OrderItemData get(int id) throws ApiException {
        OrderItemPojo p = orderItemService.get(id);
        return convert(p);
    }

    public List<OrderItemData> getAll() throws ApiException {
        return getAllConverter(orderItemService);
    }
    public List<OrderItemData> getAllWithId(int id) throws ApiException {
        return getAllWithIdConverter(orderItemService,id);
    }
    public List<OrderItemData> getAllConverter(OrderItemService orderItemService) throws ApiException {
        List<OrderItemPojo> list = orderItemService.getAll();
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for (OrderItemPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }
    public List<OrderItemData> getAllWithIdConverter(OrderItemService orderItemService,int id) throws ApiException {
        List<OrderItemPojo> list = orderItemService.getAllWithId(id);
        List<OrderItemData> list2 = new ArrayList<OrderItemData>();
        for (OrderItemPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }
    public OrderItemData convert(OrderItemPojo orderItemPojo) throws ApiException {
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setOrderId(orderItemData.getOrderId());
        orderItemData.setId(orderItemPojo.getId());
        orderItemData.setProductId(orderItemPojo.getProductId());
        orderItemData.setQuantity(orderItemPojo.getQuantity());
        orderItemData.setMrp(orderItemPojo.getMrp());
        ProductPojo productPojo = productService.getCheck(orderItemPojo.getProductId());
        orderItemData.setBarcode(productPojo.getBarcode());
        return orderItemData;
    }
}
