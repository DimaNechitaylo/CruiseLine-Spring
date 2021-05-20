package ua.training.CruiseLineSpring.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ua.training.CruiseLineSpring.entity.Cruise;
import ua.training.CruiseLineSpring.entity.OrderStatus;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {
	private Long id;
	private Long userId;
	private CruiseDto cruiseDto;
	private OrderStatus status;
}
