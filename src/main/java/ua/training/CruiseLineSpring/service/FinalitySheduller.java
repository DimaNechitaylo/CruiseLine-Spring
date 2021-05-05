package ua.training.CruiseLineSpring.service;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class FinalitySheduller {
	
	private final OrderService orderService;
	
    @Scheduled(cron = "00 00 22 * * *")
    public void reportCurrentData() {
    	orderService.finish(); //TODO Add loger
    }

}
