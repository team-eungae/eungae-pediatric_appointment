package com.playdata.eungae.admin;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.playdata.eungae.admin.service.AdminService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class RedisScheduler {
    private final AdminService adminService;

    @Scheduled(cron = "0 0 2 * * ?")
    public void updateRedis() {
        adminService.saveAllHospitalsToRedis();
    }

}
