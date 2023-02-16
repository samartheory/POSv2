package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Objects;

@Service
public class OrderItemService {

	@Autowired
	private OrderItemDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(OrderItemPojo orderItemPojo) throws ApiException {
		if(orderItemPojo.getQuantity() <= 0){
			throw new ApiException("Quantity is Invalid");
		}
		checkExistingOrderItem(orderItemPojo.getOrderId(),orderItemPojo.getProductId());
		dao.insert(orderItemPojo);
	}

	@Transactional
	public void delete(int id) {
		dao.delete(id);
	}

	@Transactional(readOnly = true)
	public OrderItemPojo get(int id) throws ApiException {
		OrderItemPojo p = dao.select(id);
		if (Objects.isNull(p)) {
			throw new ApiException("Order Item with given ID doesn't exists");
		}
		return p;
	}

	@Transactional
	public List<OrderItemPojo> getAll() {
		return dao.selectAll();
	}
	@Transactional
	public List<OrderItemPojo> getAllWithId(int id) {
		return dao.selectAllWithOrderId(id);
	}

	private void checkExistingOrderItem(int orderId, int productId) throws ApiException {
		List<OrderItemPojo> orderItemPojoList = getAllWithId(orderId);
		for (OrderItemPojo p : orderItemPojoList) {
			if(p.getProductId() == productId){
				throw new ApiException("Item already exists");
			}
		}
	}
	@Transactional(rollbackFor  = ApiException.class)//todo all public functions should be above private fucntions
	public void update(int id, OrderItemPojo p) throws ApiException {
		OrderItemPojo old = get(id);
		old.setQuantity(p.getQuantity());
	}


//	public static void normalize(OrderItemPojo p) {
//		p.se(StringUtil.toLowerCase(p.getBrand_category()));
//		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
//		p.setName(StringUtil.toLowerCase(p.getName()));
//	}
}
