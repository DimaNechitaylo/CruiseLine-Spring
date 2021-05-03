package ua.training.CruiseLineSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.training.CruiseLineSpring.entity.OrderStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private Long id;
	private Long userId;
	private Long cruiseId;
	private OrderStatus status;
}
