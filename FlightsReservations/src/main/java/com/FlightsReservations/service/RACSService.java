package com.FlightsReservations.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.repository.InMemoryRACSRepository;

@Component
public class RACSService implements IService<RACS,Long> {

	@Autowired
	InMemoryRACSRepository repository;
	
	@Override
	public RACS create(RACS t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RACS update(RACS t) {
		if (repository.findOne(t.getId()) == null)
			return null;
		return repository.update(t);
	}

	@Override
	public RACS findOne(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<RACS> findAll() {
		return repository.findAll();
	}

}
