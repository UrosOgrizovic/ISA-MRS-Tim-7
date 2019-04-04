package com.FlightsReservations.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.repository.AirlineRepository;

@Service
public class AirlineService {

	@Autowired
	private AirlineRepository repository;
	
	public Airline create(Airline t) {
		//Airline a = ;
		//if (findOne(t.getId()) == null)
			return repository.save(t);
		//return null;
	}

	public boolean update(Airline t) {
		Airline a = findOne(t.getId());
		if (a != null) {
			a.setLocation(t.getLocation());
			a.setName(t.getName());
			a.setPromoDescription(t.getPromoDescription());
			repository.save(a);
			return true;
		}
		return false;
	}

	public Airline findOne(Long id) {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public List<Airline> findAll() {
		return repository.findAll();
	}
}
