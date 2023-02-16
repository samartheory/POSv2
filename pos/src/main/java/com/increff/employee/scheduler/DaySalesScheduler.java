package com.increff.employee.scheduler;

import com.increff.employee.dto.DaySalesDto;
import com.increff.employee.util.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@EnableScheduling
@Service
public class DaySalesScheduler {
    @Autowired
    private DaySalesDto daySalesDto;
//    @Scheduled(fixedRate = 10000)

    @Scheduled(cron = "0 0 9 * * *") //TODO: move it to the properties file
    public void getPreviousDaySalesEntry() throws ApiException {
        daySalesDto.add();
    }
}
