package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemDao dao;

	@Transactional(rollbackOn = ApiException.class)
	public void add(OrderItemPojo orderItemPojo) throws ApiException {
		if(orderItemPojo.getQuantity() == 0){
			throw new ApiException("Quantity can't be zero");
		}
		itemInsideOrder(orderItemPojo.getOrderId(),orderItemPojo.getProductId());
		dao.insert(orderItemPojo);
	}

	@Transactional
	public void delete(int id) {
		dao.delete(id);
	}

	@Transactional(rollbackOn = ApiException.class)
	public OrderItemPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional
	public List<OrderItemPojo> getAll() {
		return dao.selectAll();
	}
	@Transactional
	public List<OrderItemPojo> getAllWithId(int id) {
		return dao.selectAllWithId(id);
	}
	@Transactional
	public void itemInsideOrder(int orderId,int productId) throws ApiException {
		List<OrderItemPojo> orderItemPojoList = getAllWithId(orderId);
		for (OrderItemPojo p : orderItemPojoList) {
			if(p.getProductId() == productId){
				throw new ApiException("Item already exists");
			}
		}
	}
	@Transactional(rollbackOn  = ApiException.class)
	public void update(int id, OrderItemPojo p) throws ApiException {
//		normalize(p);
		OrderItemPojo ex = getCheck(id);
		dao.update(ex);
	}

	@Transactional
	public OrderItemPojo getCheck(int id) throws ApiException {
		OrderItemPojo p = dao.select(id);
		if (Objects.nonNull(p)) {
			throw new ApiException("Order Item with given ID already exists, id: " + id);
		}
		return p;
	}

//	protected static void normalize(OrderPojo p) {
//		p.setBrand_category(StringUtil.toLowerCase(p.getBrand_category()));
//		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
//		p.setName(StringUtil.toLowerCase(p.getName()));
//	}
}
