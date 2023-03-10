package com.increff.employee.dto.helper;

import com.increff.employee.model.OrderData;
import com.increff.employee.pojo.OrderPojo;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class OrderDtoHelper {

    public static List<OrderData> getAllConverter(List<OrderPojo> list) {
        List<OrderData> list2 = new ArrayList<OrderData>();
        for (OrderPojo p : list) {
            list2.add(convert(p));
        }
        return list2;
    }
    public static OrderData convert(OrderPojo p) {
        OrderData orderData = new OrderData();
        orderData.setId(p.getId());
        orderData.setTime(zoneDateToString(p.getTime()));
        orderData.setStatus(p.isStatus());
        return orderData;
    }
    public static String zoneDateToString(ZonedDateTime time){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedString = time.format(formatter);
        return formattedString;
    }
}
