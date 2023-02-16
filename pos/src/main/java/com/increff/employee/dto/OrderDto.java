package com.increff.employee.dto;
import com.increff.employee.model.InvoiceData;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderItemDataForInvoice;
import com.increff.employee.pojo.InventPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.*;
import com.increff.employee.util.ApiException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.increff.employee.dto.OrderDtoHelper.convert;
import static com.increff.employee.dto.OrderDtoHelper.getAllConverter;

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
    public void delete(int id) {//todo also delete corresponding orderitems
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
        String url = "http://localhost:8000/invoice/api/invoice";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        List<OrderItemPojo>orderItemPojoList = orderItemService.getAllWithId(id);
        List<OrderItemDataForInvoice>orderItemDataForInvoices = new ArrayList<>();
        orderPojoToOrderDataList(orderItemPojoList,orderItemDataForInvoices);

        InvoiceData invoiceData = new InvoiceData();
        invoiceData.setOrderId(id);
        invoiceData.setOrderItemDataList(orderItemDataForInvoices);
        System.out.println(orderItemDataForInvoices.size());
        StringEntity stringEntity = new StringEntity(new Gson().toJson(invoiceData));
//        System.out.println(new Gson().toJson(invoiceData));
        httpPost.setEntity(stringEntity);
        System.out.println("executing request: " + httpPost.getRequestLine());

        HttpResponse httpResponse = httpClient.execute(httpPost);

        return EntityUtils.toString(httpResponse.getEntity());
    }
    public void orderPojoToOrderDataList(List<OrderItemPojo>orderItemPojoList,List<OrderItemDataForInvoice>orderItemDataForInvoices) throws ApiException {
        for(OrderItemPojo p:orderItemPojoList){
            OrderItemDataForInvoice orderItemDataForInvoice = new OrderItemDataForInvoice();
            ProductPojo productPojo = productService.get(p.getProductId());
            orderItemDataForInvoice.setId(p.getId());
            orderItemDataForInvoice.setMrp(p.getMrp());
            orderItemDataForInvoice.setName(productPojo.getName());
            orderItemDataForInvoice.setQuantity(p.getQuantity());
            orderItemDataForInvoices.add(orderItemDataForInvoice);
        }
    }
    public List<OrderData> getAll() {
        return getAllConverter(orderService);
    }
}
