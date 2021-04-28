package ua.training.CruiseLineSpring.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import ua.training.CruiseLineSpring.entity.Port;
import ua.training.CruiseLineSpring.exception.PortNotFoundException;
import ua.training.CruiseLineSpring.exception.ShipNotFoundException;
import ua.training.CruiseLineSpring.repository.PortRepository;

@Service
@AllArgsConstructor
public class PortService {

	private PortRepository portRepository;
	
	@Transactional
	public List<Port> getAll(){
		return portRepository.findAll();
	}
	
	@Transactional
	public Port getPort(Long id){
		return portRepository.findById(id)
				.orElseThrow(() -> new PortNotFoundException("Port not found with id -" + id));
	}
	
}
