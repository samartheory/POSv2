package com.increff.employee.controller;

import com.increff.employee.dto.InventDto;
import com.increff.employee.model.InventData;
import com.increff.employee.model.InventForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class InventApiController {

    @Autowired
    private InventDto dto;

    @ApiOperation(value = "Adds an Invent with Categories")
    @RequestMapping(path = "/api/invent", method = RequestMethod.POST)
    public void add(@RequestBody InventForm form) throws ApiException {
        dto.add(form);
    }


    @ApiOperation(value = "Deletes invent")
    @RequestMapping(path = "/api/invent/{id}", method = RequestMethod.DELETE)
    // /api/1
    public void delete(@PathVariable int id) {
        dto.delete(id);
    }

    @ApiOperation(value = "Gets an Invent by ID")
    @RequestMapping(path = "/api/invent/{id}", method = RequestMethod.GET)
    public InventData get(@PathVariable int id) throws ApiException {
        return dto.get(id);
    }

    @ApiOperation(value = "Gets list of all Invents and cats")
    @RequestMapping(path = "/api/invent", method = RequestMethod.GET)
    public List<InventData> getAll() throws ApiException {
        return dto.getAll();
    }

    @ApiOperation(value = "Updates an Invent")
    @RequestMapping(path = "/api/invent/{id}", method = RequestMethod.PUT)
    public void update(@PathVariable int id, @RequestBody InventForm f) throws ApiException {
        dto.update(id, f);
    }

}
