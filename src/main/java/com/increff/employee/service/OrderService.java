package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public void add(OrderPojo p) throws ApiException {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		ZonedDateTime now = ZonedDateTime.now();
		p.setTime(now.format(dtf));
		p.setStatus(false);
		dao.insert(p);
	}

	@Transactional(rollbackFor = ApiException.class)
	public void delete(int id) {
		dao.delete(id);
	}
	@Transactional(rollbackFor = ApiException.class)
	public void place(int id) throws ApiException {
		OrderPojo orderPojo = get(id);
		if(orderPojo.isStatus()){
			throw new ApiException("Order already Placed");
		}
		else {
			orderPojo.setStatus(true);
		}
	}

	@Transactional(rollbackFor = ApiException.class)
	public OrderPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional(readOnly = true)
	public List<OrderPojo> getAll() {
		return dao.selectAll();
	}

	@Transactional(rollbackFor = ApiException.class)
	public void update(int id, OrderPojo p) throws ApiException {
//		normalize(p);
		OrderPojo ex = getCheck(id);
		dao.update(ex);
	}

	@Transactional(readOnly = true)
	public OrderPojo getCheck(int id) throws ApiException {
		OrderPojo p = dao.select(id);
		if (Objects.isNull(p)) {
			throw new ApiException("Order with given ID does not exit, id: " + id);
		}
		return p;
	}

//	protected static void normalize(OrderPojo p) {
//		p.setBrand_category(StringUtil.toLowerCase(p.getBrand_category()));
//		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
//		p.setName(StringUtil.toLowerCase(p.getName()));
//	}
}
