package com.increff.employee.controller;

import com.increff.employee.dto.OrderItemDto;
import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderItemApiController {

    @Autowired
    private OrderItemDto dto;

    @ApiOperation(value = "Adds an Order Item")
    @RequestMapping(path = "/api/orderitem", method = RequestMethod.POST)
    public void add(@RequestBody OrderItemForm orderItemForm) throws ApiException {
        dto.add(orderItemForm);
    }
    @ApiOperation(value = "Adds Multiple Order Items and creates an new order")
    @RequestMapping(path = "/api/orderitem/new", method = RequestMethod.POST)
    public int addNew(@RequestBody List<OrderItemForm> orderItemForms) throws ApiException {
        return dto.addNew(orderItemForms);
    }
    @ApiOperation(value = "Deletes Order")
    @RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) throws ApiException {
        dto.delete(id);
    }

    @ApiOperation(value = "Gets an Order by ID")
    @RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.GET)
    public OrderItemData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
    }
    @ApiOperation(value = "Gets list of all Orders with given order id")
    @RequestMapping(path = "/api/orderitem/with/{id}", method = RequestMethod.GET)
    public List<OrderItemData> getAllWithId(@PathVariable int id) throws ApiException {
        return dto.getAllWithId(id);
    }

    @ApiOperation(value = "Gets list of all Orders")
    @RequestMapping(path = "/api/orderitem", method = RequestMethod.GET)
    public List<OrderItemData> getAll() throws ApiException {
        return dto.getAll();
    }
    @ApiOperation(value = "Gets list of all Orders within a given timeframe")
    @RequestMapping(path = "/api/orderitem/filter", method = RequestMethod.GET)
    public List<OrderItemData> getAllLimit() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Updates an Order")
    @RequestMapping(path = "/api/orderitem/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody int newQuantity) throws ApiException {
        dto.update(id, newQuantity);
    }


}
