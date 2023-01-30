package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemDataForInvoice {
    String name;
    int quantity;
    private int id;
    private double mrp;
}
