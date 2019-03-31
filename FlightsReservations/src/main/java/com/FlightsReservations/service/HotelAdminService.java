package com.FlightsReservations.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.HotelAdmin;
import com.FlightsReservations.repository.InMemoryHotelRepository;

@Component
public class HotelAdminService implements IService<HotelAdmin,Long> {

	/*
	@Autowired
	InMemoryHotelRepository repository;
	*/
	@Override
	public HotelAdmin create(HotelAdmin t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HotelAdmin update(HotelAdmin t) {
			return null;
		
	}

	@Override
	public HotelAdmin findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<HotelAdmin> findAll() {
		return null;
	}

}
