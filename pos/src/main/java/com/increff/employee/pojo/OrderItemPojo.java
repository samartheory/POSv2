package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Table(uniqueConstraints = { @UniqueConstraint(name = "orderId&productId", columnNames = { "orderId", "productId" }) })
@Entity
public class OrderItemPojo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	int id;
	int orderId;
	int productId;
	int quantity;
	double mrp;
}
