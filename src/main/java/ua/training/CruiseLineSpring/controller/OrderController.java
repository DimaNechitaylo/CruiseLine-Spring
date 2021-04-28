package ua.training.CruiseLineSpring.controller;

import static org.springframework.http.ResponseEntity.status;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.OrderDto;
import ua.training.CruiseLineSpring.service.OrderService;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping("/{shipId}")
	public ResponseEntity<OrderDto> submitOrderRequest(@PathVariable Long cruiseId){
		return status(HttpStatus.CREATED).body(orderService.submitOrderRequest(cruiseId));
	}
	
}
