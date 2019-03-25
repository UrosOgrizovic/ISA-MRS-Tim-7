package com.FlightsReservations.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.repository.InMemoryAirlineRepository;

@Component
public class AirlineService implements IService<Airline,Long>{

	@Autowired
	InMemoryAirlineRepository repository;
	
	@Override
	public Airline create(Airline t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Airline update(Airline t) {
		if (repository.findOne(t.getId()) == null)
			return null;
		return repository.update(t);
	}

	@Override
	public Airline findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Airline> findAll() {
		return repository.findAll();
	}
}
