package ua.training.CruiseLineSpring.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.OrderDto;
import ua.training.CruiseLineSpring.dto.ShipDto;
import ua.training.CruiseLineSpring.entity.Cruise;
import ua.training.CruiseLineSpring.entity.Order;
import ua.training.CruiseLineSpring.entity.OrderStatus;
import ua.training.CruiseLineSpring.entity.Ship;
import ua.training.CruiseLineSpring.entity.User;
import ua.training.CruiseLineSpring.exception.CruiseNotFoundException;
import ua.training.CruiseLineSpring.exception.ForbiddenOrderException;
import ua.training.CruiseLineSpring.exception.OrderNotFoundException;
import ua.training.CruiseLineSpring.exception.ShipNotFoundException;
import ua.training.CruiseLineSpring.repository.CruiseRepository;
import ua.training.CruiseLineSpring.repository.OrderRepository;

@Service
@AllArgsConstructor
public class OrderService {

	private final OrderRepository orderRepository;
	private final AuthService authService;
	private final CruiseService cruiseService;

	public OrderDto getOrderByCruiseIdAndUserId(Long cruiseId) {
		Order order = orderRepository.findByCruise_idAndUser_id(cruiseId, authService.getCurrentUser().getId())
				.orElseThrow(() -> new OrderNotFoundException("Order not found with cruise id -" + cruiseId));
		return mapToDto(order);
	}
	
	public OrderDto submitOrderRequest(Long cruiseId) {
		Cruise cruise = cruiseService.getCruiseByIdNotBookined(cruiseId);
		Order order = orderRepository.save(Order.builder().cruise(cruise).user(authService.getCurrentUser())
				.status(OrderStatus.PROCESSING).build());
		return mapToDto(order);
	}
	
	public OrderDto pay(Long orderId) {
		checkOwner(orderId, authService.getCurrentUser());
		checkStatus(orderId, OrderStatus.WATING_PAYMENT);
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Cruise not found with id -" + orderId));
		order.getCruise().addPassenger(authService.getCurrentUser());
		order.pay();
		return mapToDto(orderRepository.save(order));
	}

	private void checkOwner(Long orderId, User user) {
		orderRepository.findByUserAndIdAndStatusNot(authService.getCurrentUser(), orderId, OrderStatus.FINISHED)
				.orElseThrow(() -> new ForbiddenOrderException("This order is not yours"));
	}

	public OrderDto reject(Long orderId) {
		checkStatus(orderId, OrderStatus.PROCESSING);
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Cruise not found with id -" + orderId));
		order.reject();
		return mapToDto(orderRepository.save(order));
	}

	public OrderDto cancel(Long orderId) {
		checkStatus(orderId, OrderStatus.WATING_PAYMENT);
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Cruise not found with id -" + orderId));
		order.getCruise().removePassenger(authService.getCurrentUser());
		order.cancel();
		return mapToDto(orderRepository.save(order));
	}

	public OrderDto confirm(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Cruise not found with id -" + orderId));
		order.confirm();
		return mapToDto(orderRepository.save(order));
	}

	public OrderDto start(Long orderId) {
		Order order = orderRepository.findById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id - " + orderId));
		order.start();
		return mapToDto(orderRepository.save(order));
	}

//	public OrderDto finish(Long orderId) {
//		Order order = orderRepository.findById(orderId)
//				.orElseThrow(() -> new OrderNotFoundException("Cruise not found with id -" + orderId));
//		order.finish();
//		return mapToDto(orderRepository.save(order));
//	}
	
	public void start() {
		orderRepository.startCruises(OrderStatus.STARTED.toString(), OrderStatus.PAID.toString());
	}
	
	public void finish() {
		orderRepository.finishCruises(OrderStatus.FINISHED.toString(), OrderStatus.STARTED.toString());
	}

	@Transactional(readOnly = true)
	public List<OrderDto> getUserOrders() {
		Optional<List<Order>> usersOrdersOptional = orderRepository.findByUser(authService.getCurrentUser());
		List<Order> usersOrders = usersOrdersOptional
				.orElseThrow(() -> new OrderNotFoundException("No orders " + "Found with this user"));
		return usersOrders.stream().map(e -> mapToDto(e)).collect(Collectors.toList());
	}

	private void checkStatus(Long orderId, OrderStatus status) {
		OrderStatus orderStatus = getStatusByOrderId(orderId);
		if(!orderStatus.equals(status)) {
			throw new ForbiddenOrderException("There is an inappropriate status in this order, its status is " + orderStatus);
		}		
	}
	
	private OrderStatus getStatusByOrderId(Long orderId) {
		return orderRepository.findStatusById(orderId)
				.orElseThrow(() -> new OrderNotFoundException("Order not found with id - " + orderId));
	}
	
	private OrderDto mapToDto(Order order) {
		return OrderDto.builder().id(order.getId()).userId(order.getUser().getId()).cruiseDto(cruiseService.mapToDto(order.getCruise()))
				.status(order.getStatus()).build();
	}

	public List<OrderDto> getGetOrderForVerification() {
		Optional<List<Order>> ordersOptional = orderRepository.findByStatus(OrderStatus.PROCESSING);
		List<Order> orders = ordersOptional
				.orElseThrow(() -> new OrderNotFoundException("No orders " + "Found with this user"));
		return orders.stream().map(e -> mapToDto(e)).collect(Collectors.toList());
	}


}
