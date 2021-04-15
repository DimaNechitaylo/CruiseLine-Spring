package ua.training.CruiseLineSpring.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.ShipDto;
import ua.training.CruiseLineSpring.entity.Ship;
import ua.training.CruiseLineSpring.exception.ShipNotFoundException;
import ua.training.CruiseLineSpring.repository.ShipRepository;
import static java.util.stream.Collectors.toList;


@Service
@AllArgsConstructor
public class ShipService {

	private final ShipRepository shipRepository;
	private final AuthService authService;
	
	@Transactional(readOnly = true)
	public List<ShipDto> getAll() {
		return shipRepository.findAll()
				.stream()
				.map(this::mapToDto)
				.collect(toList());
	}

	@Transactional(readOnly = true)
	public ShipDto getShip(Long id) {
		Ship ship = shipRepository.findById(id)
				.orElseThrow(() -> new ShipNotFoundException("Ship not found with id -" + id));
		return mapToDto(ship);
	}

	public ShipDto save(@Valid ShipDto shipDto) {
		Ship ship = shipRepository.save(mapToShip(shipDto));
		shipDto.setId(ship.getId());
		return shipDto;
	}
	
	private ShipDto mapToDto(Ship ship) {
        return ShipDto.builder().name(ship.getName()).id(ship.getId())
        		.build();
    }

    private Ship mapToShip(ShipDto shipDto) {
        return Ship.builder().name(shipDto.getName())
        		.build();
    }

}
