package ua.training.CruiseLineSpring.service;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.UserDto;
import ua.training.CruiseLineSpring.entity.User;
import ua.training.CruiseLineSpring.repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
	
	private UserRepository userRepository;

	public UserDto findUserByUsername(String username) {
		return mapToDto(userRepository.findByUsername(username)
				.orElseThrow(()->new NullPointerException("No user found by id")));
	}
	
	private UserDto mapToDto(User user) {
		return UserDto.builder()
				.id(user.getId())
				.username(user.getUsername())
				.role(user.getRole())
				.build();
	}

}
