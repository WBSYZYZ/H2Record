package com.dashboard.scheduled;

import com.dashboard.service.WebSocketService;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author lw
 */
@EnableScheduling
@Component
public class WebSocketScheduled {

    @Resource
    private WebSocketService webSocketService;

    @Scheduled(cron = "0/15 * * * * ?")
    public void heartbeat() {
        webSocketService.heartbeat();
    }
}