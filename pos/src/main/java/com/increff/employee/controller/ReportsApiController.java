package com.increff.employee.controller;

import com.increff.employee.dto.SalesReportDto;
import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import com.increff.employee.util.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
public class ReportsApiController {

    @Autowired
    private SalesReportDto salesReportDto;

    @ApiOperation(value = "Get all sales within the form criteria")
    @RequestMapping(path = "/api/reports/sales", method = RequestMethod.POST)
    public List<SalesReportData> getAll(@RequestBody SalesReportForm form) throws ApiException {
        return salesReportDto.getSalesReport(form);
    }

//    @ApiOperation(value = "Updates an Order")
//    @RequestMapping(path = "/api/Order/{id}", method = RequestMethod.PUT)
//    public void update(@PathVariable int id, @RequestBody OrderForm f) throws ApiException {
//        dto.update(id, f);
//    }


}
