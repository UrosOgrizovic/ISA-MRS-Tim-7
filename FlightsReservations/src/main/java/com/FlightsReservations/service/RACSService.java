package com.FlightsReservations.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.Discount;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.dto.CarDTO;
import com.FlightsReservations.repository.RACSRepository;

@Component
@Transactional(readOnly = true)
public class RACSService {

	@Autowired
	RACSRepository repository;
	
	@Autowired
	CarService carService;
	
	@Transactional(readOnly = false)
	public RACS create(RACS t) {
		Set<Car> cars = t.getCars();
		if (cars != null) {
			for (Car c : cars) {
				if (c.getDiscounts() == null)
					c.setDiscounts(new HashSet<Discount>());
			}
		}
		
		return repository.save(t);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean update(RACS t) {
		RACS r = findOne(t.getId());
		if (r != null) {
			
			r.setLongitude(t.getLongitude());
			r.setLatitude(t.getLatitude());
			r.setBranchOffices(t.getBranchOffices());
			// Hibernate saves all modifications done to cars automatically
			r.setCars(t.getCars());
			
			r.setPromoDescription(t.getPromoDescription());
			r.setName(t.getName());
			r.setPricelist(t.getPricelist());
			r.setAverageScore(t.getAverageScore());
			r.setNumberOfVotes(t.getNumberOfVotes());
			repository.save(r);
			return true;
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public boolean addCar(CarDTO car) {
		Long racsID = car.getRacs_id();
		RACS racs = findOne(racsID);

		if (racs != null) {
			Car c = new Car(
					car.getManufacturer(), 
					car.getName(), 
					car.getYearOfManufacture(), 
					car.getColor(), 
					racs,
					car.getPricePerHour());

			racs.getCars().add(c);
			repository.save(racs);
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
	
	public Collection<RACS> findByName(String name) {
		try {
			return repository.findByName(name);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Collection<RACS> findAll() {
		return repository.findAll();
	}
}
