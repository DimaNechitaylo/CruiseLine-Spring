package ua.training.CruiseLineSpring.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.training.CruiseLineSpring.entity.Role;
import ua.training.CruiseLineSpring.entity.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private Long id;
	private String username;
    private Role role; 
}
