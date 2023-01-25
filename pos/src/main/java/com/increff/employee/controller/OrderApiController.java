package com.increff.employee.controller;

import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderData;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class OrderApiController {

    @Autowired
    private OrderDto dto;

//    @ApiOperation(value = "Adds an Order")
//    @RequestMapping(path = "/api/orders", method = RequestMethod.POST)
//    public void add() throws ApiException {
//        dto.add();
//    }
    @ApiOperation(value = "Places an Order")
    @RequestMapping(path = "/api/orders/{id}", method = RequestMethod.POST)
    public void place(@PathVariable int id) throws ApiException {
        dto.place(id);
    }
    @ApiOperation(value = "Deletes Order")
    @RequestMapping(path = "/api/orders/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) {
        dto.delete(id);
    }

    @ApiOperation(value = "Gets an Order by ID")
    @RequestMapping(path = "/api/orders/{id}", method = RequestMethod.GET)
    public OrderData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Gets list of all Orders")
    @RequestMapping(path = "/api/orders", method = RequestMethod.GET)
    public List<OrderData> getAll() {
        return dto.getAll();
    }

//    @ApiOperation(value = "Updates an Order")
//    @RequestMapping(path = "/api/Order/{id}", method = RequestMethod.PUT)
//    public void update(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
//        dto.update(id, f);
//    }


}
