package com.increff.employee.pojo;

import javax.persistence.*;
@Table(uniqueConstraints = { @UniqueConstraint(name = "brandandCat", columnNames = { "brand", "category" }) })
@Entity
public class BrandPojo {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@Column(nullable = false, length = 50)
	private String brand;
	@Column(nullable = false, length = 50)
	private String category;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}
}
