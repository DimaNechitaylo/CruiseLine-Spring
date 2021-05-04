package ua.training.CruiseLineSpring.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import ua.training.CruiseLineSpring.entity.Cruise;

public interface CruiseRepository extends JpaRepository<Cruise, Long> {

	Optional<List<Cruise>> findAllByStart(LocalDate start);
	Optional<List<Cruise>> findAllByStartAndFinishBetween(LocalDate start, LocalDate finish1,  LocalDate finish2);
	
	@Query(value = "select * from cruise c where finish-start between :minDuration and :maxDuration",
			nativeQuery = true)
	Optional<List<Cruise>> findAllByFinishMinusStartBetween(Long minDuration,  Long maxDuration);

}
