package ua.training.CruiseLineSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.training.CruiseLineSpring.entity.Status;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private Long id;
	private Long userId;
	private Long cruiseId;
	private Status status;
}
