package ua.training.CruiseLineSpring.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.OrderDto;
import ua.training.CruiseLineSpring.entity.Cruise;
import ua.training.CruiseLineSpring.entity.Order;
import ua.training.CruiseLineSpring.exception.CruiseNotFoundException;
import ua.training.CruiseLineSpring.exception.ShipNotFoundException;
import ua.training.CruiseLineSpring.repository.CruiseRepository;
import ua.training.CruiseLineSpring.repository.OrderRepository;
import ua.training.CruiseLineSpring.repository.ShipRepository;

@Service
@AllArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final CruiseRepository cruiseRepository;
	private final AuthService authService;
	
	public OrderDto submitOrderRequest(Long cruiseId) {
		Cruise cruise = cruiseRepository.findById(cruiseId)
				.orElseThrow(() -> new CruiseNotFoundException("Cruise not found with id -" + cruiseId));
		Order order = orderRepository.save(Order.builder()
				.cruise(cruise).client(authService.getCurrentUser())
				.build());
		return mapToDto(order);
	}
	
	
	private OrderDto mapToDto(Order order) {
        return OrderDto.builder()
        		.id(order.getId())
        		.userId(order.getClient().getId())
        		.shipId(order.getCruise().getId())
        		.build();
    }
	
}
