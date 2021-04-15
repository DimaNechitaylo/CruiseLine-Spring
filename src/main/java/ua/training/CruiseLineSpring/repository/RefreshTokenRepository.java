package ua.training.CruiseLineSpring.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import ua.training.CruiseLineSpring.entity.RefreshToken;


public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}
