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
	@Column(nullable = false)
	int orderId;
	@Column(nullable = false)
	int productId;
	@Column(nullable = false)
	int quantity;
	@Column(nullable = false)
	double mrp;
}
