package org.increff.invoice.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.increff.invoice.dto.InvoiceDto;
import org.increff.invoice.model.InvoiceData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api
@RestController
public class InvoiceController {

    @Autowired
    private InvoiceDto invoiceDto;

    @ApiOperation(value = "Generates Invoice and sends base64 string")
    @RequestMapping(path = "/api/invoice", method = RequestMethod.POST)
    public String generate(@RequestBody InvoiceData invoiceData) {
        return invoiceDto.generateString(invoiceData);
    }
}
