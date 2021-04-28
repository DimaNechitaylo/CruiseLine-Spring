package ua.training.CruiseLineSpring.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.training.CruiseLineSpring.entity.Port;

public interface PortRepository  extends JpaRepository<Port, Long> {

}
