package ua.training.CruiseLineSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.training.CruiseLineSpring.entity.Cruise;

public interface CruiseRepository extends JpaRepository<Cruise, Long> {

}
