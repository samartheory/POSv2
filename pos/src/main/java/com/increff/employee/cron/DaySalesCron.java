package com.increff.employee.cron;

import com.increff.employee.dto.DaySalesDto;
import com.increff.employee.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class DaySalesCron {
    @Autowired
    private DaySalesDto daySalesDto;

    public void process() throws ApiException {
        daySalesDto.add();
    }
}
