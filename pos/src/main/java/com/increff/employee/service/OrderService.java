package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
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
	@Transactional(readOnly = true)
	public List<OrderPojo> getByDate(String start,String end) {
		List<OrderPojo> orderPojoList = getAll();
		List<OrderPojo> toReturn = new ArrayList<>();
		if(start == "" && end == ""){
			return orderPojoList;
		}
		else if(start == ""){
			int endYear = Integer.parseInt(end.substring(0,3));
			int endMonth = Integer.parseInt(end.substring(5,6));
			int endDate = Integer.parseInt(end.substring(8,9));

			for(OrderPojo p : orderPojoList){
				//"start_date":"2023-01-26" form
				//2023/01/20 11:55:08
				String curDate = p.getTime();
				int curEndYear = Integer.parseInt(curDate.substring(0,3));
				int curEndMonth = Integer.parseInt(curDate.substring(5,6));
				int curEndDate = Integer.parseInt(curDate.substring(8,9));

				if(curEndYear < endYear){
					toReturn.add(p);
				}
				else if(curEndYear == endYear){
					if(curEndMonth < endMonth){
						toReturn.add(p);
					}
					else if(curEndMonth == endMonth){
						if(curEndDate <= endDate){
							toReturn.add(p);
						}
					}
				}
			}
		}
		else if(end == ""){
			int startYear = Integer.parseInt(start.substring(0,3));
			int startMonth = Integer.parseInt(start.substring(5,6));
			int startDate = Integer.parseInt(start.substring(8,9));
			for(OrderPojo p : orderPojoList){
				//"start_date":"2023-01-26" form
				//2023/01/20 11:55:08
				String curDate = p.getTime();
				int curStartYear = Integer.parseInt(curDate.substring(0,3));
				int curStartMonth = Integer.parseInt(curDate.substring(5,6));
				int curStartDate = Integer.parseInt(curDate.substring(8,9));

				if(curStartYear > startYear){
					toReturn.add(p);
				}
				else if(curStartYear == startYear){
					if(curStartMonth > startMonth){
						toReturn.add(p);
					}
					else if(curStartMonth == startMonth){
						if(curStartDate >= startDate){
							toReturn.add(p);
						}
					}
				}
			}
		}
		else{
			int endYear = Integer.parseInt(end.substring(0,3));
			int endMonth = Integer.parseInt(end.substring(5,6));
			int endDate = Integer.parseInt(end.substring(8,9));
			int startYear = Integer.parseInt(start.substring(0,3));
			int startMonth = Integer.parseInt(start.substring(5,6));
			int startDate = Integer.parseInt(start.substring(8,9));
			for(OrderPojo p:orderPojoList){
				String curDate = p.getTime();
				int curStartYear = Integer.parseInt(curDate.substring(0,3));
				int curStartMonth = Integer.parseInt(curDate.substring(5,6));
				int curStartDate = Integer.parseInt(curDate.substring(8,9));
				if(curStartYear <= endYear && curStartYear >= startYear){
					if(curStartMonth <= endMonth && curStartMonth >= startMonth){
						if(curStartDate <= endDate && curStartDate >= startDate){
							toReturn.add(p);
						}
					}
				}
			}
		}
		return toReturn;
	}
//	protected static void normalize(OrderPojo p) {
//		p.setBrand_category(StringUtil.toLowerCase(p.getBrand_category()));
//		p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
//		p.setName(StringUtil.toLowerCase(p.getName()));
//	}
}
