package ua.training.CruiseLineSpring.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import ua.training.CruiseLineSpring.entity.Cruise;
import ua.training.CruiseLineSpring.entity.User;

public interface CruiseRepository extends JpaRepository<Cruise, Long> {

	Optional<List<Cruise>> findAllByStart(LocalDate start);
	Optional<List<Cruise>> findAllByStartAndFinishBetween(LocalDate start, LocalDate finish1,  LocalDate finish2);
	
	@Query(value = "select * from cruise where DATEDIFF(finish, start) between :minDuration and :maxDuration",
			nativeQuery = true)
	Optional<List<Cruise>> findAllByFinishMinusStartBetween(Long minDuration,  Long maxDuration);
	
	@Query(value = "select * from cruise c " + 
			"left join orders o " + 
			"on o.cruise_id = c.id " + 
			"where c.id = :cruiseId and o.user_id not in (:userId) " + 
			"group by c.id",
			nativeQuery = true)
	Optional<Cruise> findByIdNotBookined(Long cruiseId, Long userId);
	
	@Transactional
	@Query(value = "select * from cruise c " + 
			"join orders o " + 
			"on o.cruise_id = c.id " + 
			"where o.user_id = :userId",
			nativeQuery = true)
	Optional<List<Cruise>> findUserCruisesByOrders(Long userId);
	
}
