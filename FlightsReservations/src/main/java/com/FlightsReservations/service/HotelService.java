package com.FlightsReservations.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.repository.InMemoryHotelRepository;

@Component
public class HotelService implements IService<Hotel,Long> {

	@Autowired
	InMemoryHotelRepository repository;
	
	@Override
	public Hotel create(Hotel t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Hotel update(Hotel t) {
		if (repository.findOne(t.getId()) == null)
			return null;
		return repository.update(t);
	}

	@Override
	public Hotel findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<Hotel> findAll() {
		return repository.findAll();
	}

}
