package ua.training.CruiseLineSpring.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CruiseDto {
	private Long id;
	private Long shipId;
	private int passengersCount;
	private int availableCount;
	private LocalDate start;
	private LocalDate finish;
	private List<Long> portsId;
}
