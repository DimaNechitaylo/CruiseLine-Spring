package ua.training.CruiseLineSpring.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.CruiseDto;
import ua.training.CruiseLineSpring.entity.Cruise;
import ua.training.CruiseLineSpring.exception.CruiseNotFoundException;
import ua.training.CruiseLineSpring.repository.CruiseRepository;
import ua.training.CruiseLineSpring.repository.ShipRepository;

@Service
@AllArgsConstructor
public class CruiseService {

	private final CruiseRepository cruiseRepository;
	private final ShipRepository shipRepository;
	private final PortService portService;

	
	@Transactional(readOnly = true)
	public List<CruiseDto> getAll() {
		return cruiseRepository.findAll()
				.stream()
				.map(this::mapToDto)
				.collect(toList());
	}

	@Transactional(readOnly = true)
	public CruiseDto getCruiseDto(Long id) {
		Cruise cruise = cruiseRepository.findById(id)
				.orElseThrow(() -> new CruiseNotFoundException("Cruise not found with id -" + id));
		return mapToDto(cruise);
	}

	public CruiseDto save(@Valid CruiseDto CruiseDto) {
		Cruise cruise = cruiseRepository.save(mapToCruise(CruiseDto));
		CruiseDto.setId(cruise.getId());
		CruiseDto.setAvailableCount(cruise.getShip().getPassengerСapacity());
		return CruiseDto;
	}
	
	private CruiseDto mapToDto(Cruise cruise) {
        return CruiseDto.builder()
        		.id(cruise.getId())
        		.passengersCount(cruise.getPassengers().size())
        		.availableCount(cruise.getShip().getPassengerСapacity() - cruise.getPassengers().size())
        		.shipId(cruise.getShip().getId())
        		.start(cruise.getStart())
        		.finish(cruise.getFinish())
        		.portsId(cruise.getPorts().stream()
        				.map(e -> e.getId())
        				.collect(Collectors.toList()))
        		.build();
    }
	
	private Cruise mapToCruise(CruiseDto cruiseDto) {
        return Cruise.builder()
        		.ship(shipRepository.getOne(cruiseDto.getShipId()))
        		.start(cruiseDto.getStart())
        		.finish(cruiseDto.getFinish())
        		.ports(cruiseDto.getPortsId()
        				.stream()
        				.map(e -> portService.getPort(e))
        				.collect(Collectors.toList()))
        		.build();
    }


}
