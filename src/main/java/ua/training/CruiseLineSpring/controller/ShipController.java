package ua.training.CruiseLineSpring.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.ShipDto;
import ua.training.CruiseLineSpring.service.ShipService;

@RestController
@RequestMapping("/api/ship")
@AllArgsConstructor
public class ShipController {
	
	private final ShipService shipService;
	
	@GetMapping
	public List<ShipDto> getAllShip(){
		return shipService.getAll();
	}
	
	@GetMapping("/{id}")
	public ShipDto getShip(@PathVariable Long id) {
		return shipService.getShip(id);
	}
	
	@PostMapping
	public ShipDto create(@RequestBody @Valid ShipDto shipDto) {
		return shipService.save(shipDto);
	}
}
