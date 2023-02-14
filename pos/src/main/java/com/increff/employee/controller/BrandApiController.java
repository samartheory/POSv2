package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.dto.BrandDto;
import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import com.increff.employee.service.ApiException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
//additions
//todo make commons layer and put common files
//todo convert all exceptions in invoice as api exceptions
//todo make scheduler which updates every 1 day(5 minutes for testing) even if there are no orders
//todo write tests for dto
//todo make a abstract class that has timestamp for each update, delete
//todo make login system
//edits
//todo instead of calling isNull for strings use isEmpty in StringUtil.java
//todo toasts
//todo hide id in brand and instead use serial number in js

@Api
@RestController
public class BrandApiController {

    @Autowired
    private BrandDto dto;

    @ApiOperation(value = "Adds an Brand with Categories")
    @RequestMapping(path = "/api/brand", method = RequestMethod.POST)
    public void add(@RequestBody BrandForm form) throws ApiException {
        dto.add(form);
    }


    @ApiOperation(value = "Deletes brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) {
        dto.delete(id);
    }

    @ApiOperation(value = "Gets an brand by ID")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.GET)
    public BrandData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Gets list of all Brands and cats")
    @RequestMapping(path = "/api/brand", method = RequestMethod.GET)
    public List<BrandData> getAll() {
        return dto.getAll();
    }

    @ApiOperation(value = "Updates an Brand")
    @RequestMapping(path = "/api/brand/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody BrandForm f) throws ApiException {
        dto.update(id, f);
    }

}
