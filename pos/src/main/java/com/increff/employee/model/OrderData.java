package com.increff.employee.model;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
@Getter
@Setter
public class OrderData {
	private int id;
	private String time;
	private boolean status;
}
