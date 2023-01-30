package com.increff.employee.model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class InvoiceData {
    private int orderId;
//    private ZonedDateTime datetime;
    List<OrderItemDataForInvoice> orderItemDataList;
}
