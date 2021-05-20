package ua.training.CruiseLineSpring.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class Sheduller {
	
	private final OrderService orderService;
	
    @Scheduled(cron = "00 00 22 * * *")
    public void startCruises() {
    	orderService.start(); //TODO Add loger
    }
    
    @Scheduled(cron = "00 00 22 * * *")
    public void finishCruises() {
    	orderService.finish(); //TODO Add loger
    }
    
}
