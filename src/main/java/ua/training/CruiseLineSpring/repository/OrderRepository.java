package ua.training.CruiseLineSpring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.training.CruiseLineSpring.entity.Order;
import ua.training.CruiseLineSpring.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	Optional<List<Order>> findByUser(User user);
}
