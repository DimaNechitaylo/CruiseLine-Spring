package ua.training.CruiseLineSpring.controller;

import static org.springframework.http.HttpStatus.OK;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.UserDto;
import ua.training.CruiseLineSpring.service.UserService;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
	
	private final UserService userService;
	
	@GetMapping("/getByUsername")
	public ResponseEntity<UserDto> getUserByUsername(@RequestParam(required = true) String username){
		return ResponseEntity.status(OK).body(userService.findUserByUsername(username));
	}

}
