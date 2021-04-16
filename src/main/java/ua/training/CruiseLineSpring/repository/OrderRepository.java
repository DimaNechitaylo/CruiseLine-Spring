package ua.training.CruiseLineSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.training.CruiseLineSpring.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
}
