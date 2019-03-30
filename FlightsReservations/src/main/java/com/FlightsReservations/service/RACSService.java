package com.FlightsReservations.service;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.repository.RACSRepository;

@Component
public class RACSService {

	@Autowired
	RACSRepository repository;
	
	public RACS create(RACS t) {
		return repository.save(t);
	}

	public boolean update(RACS t) {
		RACS r = findOne(t.getId());
		if (r != null) {
			r.setAddress(t.getAddress());
			r.setBranchOffices(t.getBranchOffices());
			r.setCars(t.getCars());
			r.setDescription(t.getDescription());
			r.setName(t.getName());
			r.setPricelist(t.getPricelist());
			repository.save(r);
			return true;
		}
		return false;
	}

	public RACS findOne(Long id) {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	public Collection<RACS> findAll() {
		return repository.findAll();
	}

}
