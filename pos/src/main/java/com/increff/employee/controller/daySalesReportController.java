package com.increff.employee.controller;

import com.increff.employee.dto.DaySalesDto;
import com.increff.employee.model.DaySalesData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class daySalesReportController {

    @Autowired
    private DaySalesDto daySalesDto;
    @ApiOperation(value = "gets all the report data")
    @RequestMapping(path = "/api/daySalesReport", method = RequestMethod.GET)
    public List<DaySalesData> getAll() {
        return daySalesDto.getAll();
    }
}
