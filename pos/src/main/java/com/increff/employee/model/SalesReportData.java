package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesReportData{
	String brand;
	String category;
	private int quantity;
	private double total;
}
