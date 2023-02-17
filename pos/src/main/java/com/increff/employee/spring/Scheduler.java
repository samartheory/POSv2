package com.increff.employee.spring;

import com.increff.employee.cron.DaySalesCron;
import com.increff.employee.dto.DaySalesDto;
import com.increff.employee.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class Scheduler {
    @Autowired
    private DaySalesCron daySalesCron;
    @Scheduled(cron = "0 0 9 * * *")
    public void getPreviousDaySalesEntry() throws ApiException {
        daySalesCron.process();
    }
}
