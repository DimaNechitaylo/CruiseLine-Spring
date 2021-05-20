package ua.training.CruiseLineSpring.service;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.dto.CruiseDto;
import ua.training.CruiseLineSpring.dto.OrderDto;
import ua.training.CruiseLineSpring.entity.Cruise;
import ua.training.CruiseLineSpring.entity.Order;
import ua.training.CruiseLineSpring.exception.CruiseNotFoundException;
import ua.training.CruiseLineSpring.exception.OrderNotFoundException;
import ua.training.CruiseLineSpring.repository.CruiseRepository;
import ua.training.CruiseLineSpring.repository.ShipRepository;

@Service
@AllArgsConstructor
public class CruiseService {

	private final CruiseRepository cruiseRepository;
	private final ShipRepository shipRepository;
	private final PortService portService;
	private final AuthService authService;


	
	@Transactional(readOnly = true)
	public List<CruiseDto> getAll() {
		return cruiseRepository.findAll()
				.stream()
				.map(this::mapToDto)
				.collect(toList());
	}
	
	public List<CruiseDto> filterByDate(LocalDate date) {
		Optional<List<Cruise>> filteredCruiseOptional = cruiseRepository.findAllByStart(date);
		List<Cruise> filteredCruise = filteredCruiseOptional.orElseThrow(() -> new CruiseNotFoundException("No orders " + "Found with this user"));
		return filteredCruise
				.stream()
				.map(this::mapToDto)
				.collect(toList());
	}
	
	public List<CruiseDto> filter(LocalDate start, Long minDuration, Long maxDuration) {
		Optional<List<Cruise>> filteredCruiseOptional = cruiseRepository.findAllByStartAndFinishBetween(start, start.plusDays(minDuration), start.plusDays(maxDuration));
		List<Cruise> filteredCruise = filteredCruiseOptional.orElseThrow(() -> new CruiseNotFoundException("No orders " + "Found with this user"));
		return filteredCruise
				.stream()
				.map(this::mapToDto)
				.collect(toList());
	}
	public List<CruiseDto> filter(Long minDuration, Long maxDuration) {  //TODO fix invalid date subtraction
		Optional<List<Cruise>> filteredCruiseOptional = cruiseRepository.findAllByFinishMinusStartBetween(minDuration, maxDuration);
		List<Cruise> filteredCruise = filteredCruiseOptional.orElseThrow(() -> new CruiseNotFoundException("No orders " + "Found with this user"));
		return filteredCruise
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
	
	public Cruise getCruiseByIdNotBookined(Long cruiseId){
		return cruiseRepository.findByIdNotBookined(cruiseId, authService.getCurrentUser().getId())
				.orElseThrow(() -> new CruiseNotFoundException("Cruise not found with id -" + cruiseId));

	}

	
	public CruiseDto mapToDto(Cruise cruise) {
        return CruiseDto.builder()
        		.id(cruise.getId())
        		.name(cruise.getName())
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
        		.name(cruiseDto.getName())
        		.start(cruiseDto.getStart())
        		.finish(cruiseDto.getFinish())
        		.ports(cruiseDto.getPortsId()
        				.stream()
        				.map(e -> portService.getPort(e))
        				.collect(Collectors.toList()))
        		.build();
    }

	public List<CruiseDto> getUserCruises() {
		Optional<List<Cruise>> userCruisesOptional = cruiseRepository.findUserCruisesByOrders(authService.getCurrentUser().getId());
		List<Cruise> userCruise = userCruisesOptional
				.orElseThrow(() -> new CruiseNotFoundException("No cruise orders found with this user"));
		return userCruise.stream().map(e -> mapToDto(e)).collect(Collectors.toList());
	}
	
}
