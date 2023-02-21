package com.increff.employee.dto;
import com.increff.employee.client.InvoiceClient;
import com.increff.employee.model.OrderData;
import com.increff.employee.pojo.InventPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.service.*;
import com.increff.employee.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

import static com.increff.employee.dto.helper.OrderDtoHelper.convert;
import static com.increff.employee.dto.helper.OrderDtoHelper.getAllConverter;

@Service
public class OrderDto {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private InvoiceClient invoiceClient;

    @Transactional(rollbackFor = ApiException.class)
    public void place(int id) throws ApiException {
        List<OrderItemPojo>orderItemPojoList = orderItemService.getAllWithId(id);
        if(orderItemPojoList.size() == 0){
            throw new ApiException("Order doesn't have any items");
        }
        for(OrderItemPojo p:orderItemPojoList){
            InventPojo inventPojo = inventoryService.get(p.getProductId());
            int availableQuantity = inventPojo.getQuantity();
            if(availableQuantity - p.getQuantity() < 0){
                throw new ApiException("Not enough items, Available item(s) = " + availableQuantity);
            }
            inventoryService.updateQuantity(p.getProductId(),availableQuantity-p.getQuantity());
        }
        orderService.place(id);
    }
    @Transactional
    public void delete(int id) {
        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllWithId(id);
        for(OrderItemPojo p : orderItemPojoList){
            orderItemService.delete(p.getId());
        }
        orderService.delete(id);
    }

    public OrderData get(int id) throws ApiException {
        OrderPojo p = orderService.get(id);
        return convert(p);
    }
    public String getPdfString(int id) throws ApiException, IOException {
        OrderPojo orderPojo = orderService.get(id);
        if(!orderPojo.isStatus()){
            throw new ApiException("Order is not yet placed");
        }
        return invoiceClient.pdfString(id);
    }

    public List<OrderData> getAll() {
        return getAllConverter(orderService.getAll());
    }
}
