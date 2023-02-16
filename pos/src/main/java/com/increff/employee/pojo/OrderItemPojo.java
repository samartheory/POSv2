package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Getter
@Setter
@Entity
public class OrderItemPojo {//todo orderid, product id unique constraint
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	int orderId;
	int productId;
	int quantity;
	double mrp;
}
