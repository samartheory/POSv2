package com.increff.employee.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AppUiController extends AbstractUiController {

	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/brand")
	public ModelAndView brand() {
		return mav("brand.html");
	}

	@RequestMapping(value = "/ui/employee")
	public ModelAndView employee() {
		return mav("employee.html");
	}

	@RequestMapping(value = "/ui/inventory")
	public ModelAndView inventory() {
		return mav("inventory.html");
	}

	@RequestMapping(value = "/ui/product")
	public ModelAndView product() {
		return mav("product.html");
	}
	@RequestMapping(value = "/ui/orders")
	public ModelAndView orders() {
		return mav("orders.html");
	}
	@RequestMapping(value = "/ui/orderitem/{id}")
	public ModelAndView orderItem(@PathVariable int id) {
		return mav("order-item.html");
	}
	@RequestMapping(value = "/ui/orderitemplaced/{id}")
	public ModelAndView orderItemPlaced(@PathVariable int id) {
		return mav("order-item-placed.html");
	}
}
