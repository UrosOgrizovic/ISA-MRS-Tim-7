package com.FlightsReservations.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.RACSAdmin;

@Component
public class RACSAdminService implements IService<RACSAdmin,Long> {

	/*
	@Autowired
	InMemoryHotelAdnubRepository repository;
	*/
	
	@Override
	public RACSAdmin create(RACSAdmin t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RACSAdmin update(RACSAdmin t) {
			return null;
		
	}

	@Override
	public RACSAdmin findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<RACSAdmin> findAll() {
		return null;
	}

}
