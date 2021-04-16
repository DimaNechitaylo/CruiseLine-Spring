package ua.training.CruiseLineSpring.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.OrderDto;
import ua.training.CruiseLineSpring.entity.Order;
import ua.training.CruiseLineSpring.entity.Ship;
import ua.training.CruiseLineSpring.exception.ShipNotFoundException;
import ua.training.CruiseLineSpring.repository.OrderRepository;
import ua.training.CruiseLineSpring.repository.ShipRepository;

@Service
@AllArgsConstructor
public class OrderService {
	
	private final OrderRepository orderRepository;
	private final ShipRepository shipRepository;
	private final AuthService authService;
	
	public OrderDto submitOrderRequest(Long shipId) {
		Ship ship = shipRepository.findById(shipId)
				.orElseThrow(() -> new ShipNotFoundException("Ship not found with id -" + shipId));
		Order order = orderRepository.save(Order.builder()
				.ship(ship).client(authService.getCurrentUser())
				.build());
		return mapToDto(order);
	}
	
	
	private OrderDto mapToDto(Order order) {
        return OrderDto.builder()
        		.id(order.getId())
        		.userId(order.getClient().getId())
        		.shipId(order.getShip().getId())
        		.build();
    }
	
}
