package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderService {

	@Autowired
	private OrderDao dao;

	@Transactional(rollbackFor = ApiException.class)
	public OrderPojo add() throws ApiException {
//		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		OrderPojo p = new OrderPojo();
		p.setTime(ZonedDateTime.now());
		p.setStatus(false);
		dao.insert(p);
		return p;
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

	@Transactional
	public OrderPojo get(int id) throws ApiException {
		return getCheck(id);
	}

	@Transactional(readOnly = true)
	public List<OrderPojo> getAll() {
		return dao.selectAll();
	}


	@Transactional(readOnly = true)
	public OrderPojo getCheck(int id) throws ApiException {
		OrderPojo p = dao.select(id);
		if (Objects.isNull(p)) {
			throw new ApiException("Order with given ID does not exit, id: " + id);
		}
		return p;
	}
	@Transactional(readOnly = true)
	public List<OrderPojo> getBetweenDates(ZonedDateTime startDate, ZonedDateTime endDate) {
		return dao.selectBetweeenDates(startDate, endDate);
	}
	@Transactional(readOnly = true)
	public List<OrderPojo> getByDate(String start,String end) {
		List<OrderPojo> orderPojoList = getAll();
		return dateHelper(orderPojoList,start,end);
	}

	private List<OrderPojo> dateHelper(List<OrderPojo> orderPojoList, String start, String end) {
		List<OrderPojo> toReturn = new ArrayList<>();
		if(start == "" && end == ""){
			return orderPojoList;
		}
		else if(start == ""){
			for(OrderPojo p : orderPojoList){
				if(zoneDateToString(p.getTime()).compareTo(end) <= 0){
					toReturn.add(p);
				}
			}
		}
		else if(end == ""){
			for(OrderPojo p : orderPojoList) {
				if(zoneDateToString(p.getTime()).compareTo(start) >= 0){
					toReturn.add(p);
				}
			}
		}
		else{
			for(OrderPojo p:orderPojoList){
				System.out.println(zoneDateToString(p.getTime()).compareTo(start));
				System.out.println(start);

				if(zoneDateToString(p.getTime()).compareTo(start) >= 0 && zoneDateToString(p.getTime()).compareTo(end) <= 0){
					toReturn.add(p);
				}
			}
		}
		return toReturn;
	}

	private String zoneDateToString(ZonedDateTime time){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedString = time.format(formatter);
		return formattedString;
	}
//	protected static void normalize(OrderPojo p) {
//		p.setBrand_category(StringUtil.toLowerCase(p.getBrand_category()));
//		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
//		p.setName(StringUtil.toLowerCase(p.getName()));
//	}
}
