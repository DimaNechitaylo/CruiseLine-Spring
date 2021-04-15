package ua.training.CruiseLineSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.training.CruiseLineSpring.entity.Ship;

public interface ShipRepository extends JpaRepository<Ship, Long> {

}
