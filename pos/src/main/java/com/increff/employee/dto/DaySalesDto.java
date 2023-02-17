package com.increff.employee.dto;

import com.increff.employee.model.DaySalesData;
import com.increff.employee.pojo.DaySalesPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.util.ApiException;
import com.increff.employee.service.DaySalesService;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
public class DaySalesDto {

    @Autowired
    private DaySalesService daySalesService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    public void add() throws ApiException {
        ZonedDateTime prevDateStart = getPrevStart();
        ZonedDateTime prevDateEnd = getPrevEnd();
        List<OrderPojo> orderPojos = orderService.getBetweenDates(prevDateStart, prevDateEnd);
        if (orderPojos.isEmpty()) {
            return;
        }
        int prevDayOrderItemsCount = 0;
        double totalRevenue = 0;
        for (OrderPojo orderPojo : orderPojos) {
            List<OrderItemPojo> orderItemPojos = orderItemService.getAllWithId(orderPojo.getId());
            for (OrderItemPojo orderItemPojo : orderItemPojos) {
                prevDayOrderItemsCount += orderItemPojo.getQuantity();
                totalRevenue += orderItemPojo.getMrp();
            }
        }
        DaySalesPojo daySalesPojo = convert(prevDateStart, orderPojos.size(), prevDayOrderItemsCount, totalRevenue);
        if(daySalesService.presentWithDate(prevDateStart)) {//todo move presentwithdate to add inside service
            daySalesService.add(daySalesPojo);
        }
    }

    public List<DaySalesData> getAll() {
        List<DaySalesPojo> daySalesPojoList = daySalesService.getAll();
        List<DaySalesData> daySalesDataList = new ArrayList<>();
        for (DaySalesPojo daySalesPojo: daySalesPojoList) {
            daySalesDataList.add(convert(daySalesPojo));
        }
        return daySalesDataList;
    }

    private ZonedDateTime getPrevStart() {
            return LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault());
    }
    private ZonedDateTime getPrevEnd() {
            return LocalDate.now().minusDays(1).atStartOfDay(ZoneId.systemDefault()).withHour(23).withMinute(59).withSecond(59);

    }
    private DaySalesData convert(DaySalesPojo daySalesPojo) {
        DaySalesData daySalesData = new DaySalesData();
        daySalesData.setDate(daySalesPojo.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        daySalesData.setOrderCount(daySalesPojo.getInvoiced_orders_count());
        daySalesData.setOrderItemCount(daySalesPojo.getInvoiced_items_count());
        daySalesData.setTotalRevenue(daySalesPojo.getTotal_revenue());
        return daySalesData;
    }
    private DaySalesPojo convert(ZonedDateTime entryDate, int dayOrderCount, int dayOrderItemCount, double revenue) {
        DaySalesPojo daySalesPojo = new DaySalesPojo();
        daySalesPojo.setDate(entryDate);
        daySalesPojo.setInvoiced_orders_count(dayOrderCount);
        daySalesPojo.setInvoiced_items_count(dayOrderItemCount);
        daySalesPojo.setTotal_revenue(revenue);
        return daySalesPojo;
    }

}
