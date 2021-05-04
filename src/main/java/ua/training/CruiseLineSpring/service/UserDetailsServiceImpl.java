package ua.training.CruiseLineSpring.service;


import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.UserDetailsImpl;
import ua.training.CruiseLineSpring.entity.User;
import ua.training.CruiseLineSpring.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) {
		Optional<User> userOptional = userRepository.findByUsername(username);
		User user = userOptional
				.orElseThrow(() -> new UsernameNotFoundException("No user " + "Found with username : " + username));

		return new UserDetailsImpl(user);
	}

}