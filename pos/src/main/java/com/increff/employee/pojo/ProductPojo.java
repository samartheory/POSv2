package com.increff.employee.pojo;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class ProductPojo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)//todo generation type table
	private int id;
	@Column(nullable = false)
	private String barcode;//todo unique constraints
	@Column(nullable = false)
	private int brand_category;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private double mrp;
}
