package ua.training.CruiseLineSpring.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.OrderDto;
import ua.training.CruiseLineSpring.entity.Cruise;
import ua.training.CruiseLineSpring.entity.Order;
import ua.training.CruiseLineSpring.entity.Status;
import ua.training.CruiseLineSpring.entity.User;
import ua.training.CruiseLineSpring.exception.CruiseNotFoundException;
import ua.training.CruiseLineSpring.exception.OrderNotFoundException;
import ua.training.CruiseLineSpring.repository.CruiseRepository;
import ua.training.CruiseLineSpring.repository.OrderRepository;

@Service
@AllArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final CruiseRepository cruiseRepository;
	private final AuthService authService;

	public OrderDto submitOrderRequest(Long cruiseId) {
		Cruise cruise = cruiseRepository.findById(cruiseId)
				.orElseThrow(() -> new CruiseNotFoundException("Cruise not found with id -" + cruiseId));
		Order order = orderRepository.save(
				Order.builder().cruise(cruise).user(authService.getCurrentUser()).status(Status.PROCESSING).build());
		return mapToDto(order);
	}

	public OrderDto pay(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Cruise not found with id -" + orderId));
		order.getCruise().addPassenger(authService.getCurrentUser());
		order.pay();
		return mapToDto(orderRepository.save(order));
	}
	public OrderDto denied(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Cruise not found with id -" + orderId));
		order.denied();
		return mapToDto(orderRepository.save(order));
	}
	public OrderDto confirm(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Cruise not found with id -" + orderId));
		order.confirm();
		return mapToDto(orderRepository.save(order));
	}
	public OrderDto finish(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Cruise not found with id -" + orderId));
		order.finish();
		return mapToDto(orderRepository.save(order));
	}
	
	@Transactional(readOnly = true)
	public List<OrderDto> getUserOrders() {
		Optional<List<Order>> usersOrdersOptional = orderRepository.findByUser(authService.getCurrentUser());
		List<Order> usersOrders = usersOrdersOptional.orElseThrow(() -> new OrderNotFoundException("No orders " + "Found with this user"));
		return usersOrders.stream()
				.map(e -> mapToDto(e))
				.collect(Collectors.toList());
	}

	private OrderDto mapToDto(Order order) {
		return OrderDto.builder().id(order.getId()).userId(order.getUser().getId())
				.cruiseId(order.getCruise().getId()).status(order.getStatus()).build();
	}



}
