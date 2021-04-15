package ua.training.CruiseLineSpring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.training.CruiseLineSpring.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
}
