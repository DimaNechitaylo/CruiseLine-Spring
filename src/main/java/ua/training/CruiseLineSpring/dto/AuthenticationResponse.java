package ua.training.CruiseLineSpring.dto;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponse {
	private String authenticationToken;
	private String refreshToken;
	private String username;
	private Instant expiresAt;
}
