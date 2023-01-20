package com.increff.employee.dto;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


public class OrderItemDtoHelper {
    public static OrderItemPojo productToOrderItem(ProductPojo productPojo,OrderItemForm orderItemForm) throws ApiException {
        OrderItemPojo orderItemPojo = new OrderItemPojo();
        orderItemPojo.setProductId(productPojo.getId());
        orderItemPojo.setQuantity(orderItemForm.getQuantity());
        orderItemPojo.setMrp(productPojo.getMrp());
        orderItemPojo.setOrderId(orderItemForm.getOrderId());

        return orderItemPojo;
    }


//    public static OrderItemData convertWithBarcode(OrderItemPojo orderItemPojo){
//        OrderItemData orderItemData = new OrderItemData();
//        orderItemData.setOrderId(orderItemData.getOrderId());
//        orderItemData.setId(orderItemPojo.getId());
//        orderItemData.setProductId(orderItemPojo.getProductId());
//        orderItemData.setQuantity(orderItemPojo.getQuantity());
//        orderItemData.setMrp(orderItemPojo.getMrp());
//        return orderItemData;
//    }

}
