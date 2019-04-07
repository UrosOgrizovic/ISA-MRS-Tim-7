package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.domain.dto.AirlineDTO;
import com.FlightsReservations.repository.AirlineRepository;

@Service
public class AirlineService {

	@Autowired
	private AirlineRepository repository;
	
	public AirlineDTO create(AirlineDTO t) {
		Airline a = repository.findByName(t.getName());
		if (a == null) {
			a = new Airline(
					t.getName(), 
					t.getLongitude(), 
					t.getLatitude(), 
					t.getPromoDescription(), 
					0, 0);
			repository.save(a);
			return new AirlineDTO(a);
		}
		return null;
	}

	public boolean update(AirlineDTO t) {
		Airline a = repository.findByName(t.getName());
		if (a != null) {
			a.setName(t.getName());
			a.setLongitude(t.getLongitude());
			a.setLatitude(t.getLatitude());
			a.setPromoDescription(t.getPromoDescription());
			repository.save(a);
			return true;
		}
		return false;
	}

	public Airline findOne(Long id) {
		Optional<Airline> o = repository.findById(id);
		if (o.isPresent())
			return o.get();
		return null;
	}
	
	public List<AirlineDTO> findAll() {
		List<AirlineDTO> dtos = new ArrayList<>();
		for (Airline a : repository.findAll()) 
			dtos.add(new AirlineDTO(a));
		return dtos;
	}	
}
