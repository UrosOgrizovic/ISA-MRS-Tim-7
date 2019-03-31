package com.FlightsReservations.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.AirlineAdmin;
import com.FlightsReservations.repository.InMemoryHotelRepository;

@Component
public class AirlineAdminService implements IService<AirlineAdmin,Long> {

	/*
	@Autowired
	InMemoryHotelRepository repository;//TODO: will be deleted
	*/
	
	@Override
	public AirlineAdmin create(AirlineAdmin t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AirlineAdmin update(AirlineAdmin t) {
			return null;
		
	}

	@Override
	public AirlineAdmin findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<AirlineAdmin> findAll() {
		return null;
	}

}
