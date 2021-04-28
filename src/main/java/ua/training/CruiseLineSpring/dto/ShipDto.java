package ua.training.CruiseLineSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShipDto {
	private Long id;
	private String name;
	private int passengerСapacity;
}
