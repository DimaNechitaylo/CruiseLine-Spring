package ua.training.CruiseLineSpring.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ua.training.CruiseLineSpring.entity.Cruise;
import ua.training.CruiseLineSpring.entity.Order;
import ua.training.CruiseLineSpring.entity.OrderStatus;
import ua.training.CruiseLineSpring.entity.User;

public interface OrderRepository extends JpaRepository<Order, Long> {
	Optional<List<Order>> findByUser(User user);
	Optional<Order> findByUserAndIdAndStatusNot(User user, Long order_id, OrderStatus status);
	
	@Transactional(readOnly = true)
	Optional<Order> findByCruise_idAndUser_id(Long cruiseId, Long id);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE \n" + 
			"orders o\n" + 
			"INNER JOIN cruise c \n" + 
			"ON o.cruise_id = c.id \n" + 
			"SET \n" + 
			"status = :status \n" + 
			"WHERE  \n" + 
			"c.finish < NOW() AND status = :currentStatus",
			nativeQuery = true)
	void finishCruises(String status, String currentStatus);
	
	@Transactional
	@Modifying
	@Query(value = "UPDATE \n" + 
			"orders o\n" + 
			"INNER JOIN cruise c \n" + 
			"ON o.cruise_id = c.id \n" + 
			"SET \n" + 
			"status = :status \n" + 
			"WHERE  \n" + 
			"c.start < NOW() AND status = :currentStatus",
			nativeQuery = true)
	void startCruises(String status, String currentStatus);
	
	@Transactional
	@Query(value = "SELECT status FROM orders WHERE id = :orderId",
			nativeQuery = true)
	Optional<OrderStatus> findStatusById(Long orderId);
	
	
	Optional<List<Order>> findByStatus(OrderStatus processing);
}
