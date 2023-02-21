package com.increff.employee.client;

import com.google.gson.Gson;
import com.increff.employee.model.InvoiceData;
import com.increff.employee.model.OrderItemDataForInvoice;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.ApiException;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
@Service
public class InvoiceClient {
    @Autowired
    OrderItemService orderItemService;
    @Autowired
    ProductService productService;
    public String pdfString(int id) throws ApiException, IOException {
        String url = "http://localhost:8000/invoice/api/invoice";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        List<OrderItemPojo> orderItemPojoList = orderItemService.getAllWithId(id);
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
    public void orderPojoToOrderDataList(List<OrderItemPojo> orderItemPojoList, List<OrderItemDataForInvoice> orderItemDataForInvoices) throws ApiException {
        for(OrderItemPojo p:orderItemPojoList){
            OrderItemDataForInvoice orderItemDataForInvoice = new OrderItemDataForInvoice();
            ProductPojo productPojo = productService.get(p.getProductId());
            orderItemDataForInvoice.setId(p.getId());//todo put these line into helper
            orderItemDataForInvoice.setMrp(p.getMrp());
            orderItemDataForInvoice.setName(productPojo.getName());
            orderItemDataForInvoice.setQuantity(p.getQuantity());
            orderItemDataForInvoices.add(orderItemDataForInvoice);
        }
    }
}
