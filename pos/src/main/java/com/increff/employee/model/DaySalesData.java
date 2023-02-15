package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DaySalesData {

    private String date;
    private int orderItemCount;
    private int orderCount;
    private double totalRevenue;
}
