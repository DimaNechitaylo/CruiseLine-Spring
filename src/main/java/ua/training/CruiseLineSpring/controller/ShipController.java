package ua.training.CruiseLineSpring.controller;

import static org.springframework.http.ResponseEntity.status;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/api/ship/")
@AllArgsConstructor
public class ShipController {
	
	private final ShipService shipService;
	
	@GetMapping
	public ResponseEntity<List<ShipDto>> getAllCruises(){
		return status(HttpStatus.OK).body(shipService.getAll());
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<ShipDto> getCruise(@PathVariable Long id) {
		return status(HttpStatus.OK).body(shipService.getShip(id));
	}
	
	@PostMapping
	public ResponseEntity<ShipDto> create(@RequestBody @Valid ShipDto cruiseDto) {
		return status(HttpStatus.CREATED).body(shipService.save(cruiseDto));
	}
	
}
//@RestController
//@RequestMapping("/cruise")
//@AllArgsConstructor
//public class CruiseController {
//	
//	private final CruiseService cruiseService;
//	
//	@GetMapping
//	public ResponseEntity<List<CruiseDto>> getAllCruises(){
//		return status(HttpStatus.OK).body(cruiseService.getAll());
//	}
//	
//	@GetMapping("/{id}")
//	public ResponseEntity<CruiseDto> getCruise(@PathVariable Long id) {
//		return status(HttpStatus.OK).body(cruiseService.getCruiseDto(id));
//	}
//	
//	@PostMapping
//	public ResponseEntity<CruiseDto> create(@RequestBody @Valid CruiseDto cruiseDto) {
//		return status(HttpStatus.CREATED).body(cruiseService.save(cruiseDto));
//	}
//
//}
