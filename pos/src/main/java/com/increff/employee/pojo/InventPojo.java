package com.increff.employee.pojo;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class InventPojo {

	@Id
	private int id;
	@Column(nullable = false)
	private int quantity;
}
