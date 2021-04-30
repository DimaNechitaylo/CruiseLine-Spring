package ua.training.CruiseLineSpring.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.CruiseDto;
import ua.training.CruiseLineSpring.dto.OrderDto;
import ua.training.CruiseLineSpring.service.OrderService;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {
	
	private final OrderService orderService;
	
	@PostMapping("/{cruiseId}")
	public ResponseEntity<OrderDto> submitOrderRequest(@PathVariable Long cruiseId){
		return status(HttpStatus.CREATED).body(orderService.submitOrderRequest(cruiseId));
	}
	
	@GetMapping("/getUserOrders")
	public ResponseEntity<List<OrderDto>> getUserOrders(){
		return status(HttpStatus.OK).body(orderService.getUserOrders());
	}
	
	@PostMapping("/pay/{orderId}")
	public ResponseEntity<OrderDto> pay(@PathVariable Long orderId){
		return status(HttpStatus.OK).body(orderService.pay(orderId));
	}
	
}
