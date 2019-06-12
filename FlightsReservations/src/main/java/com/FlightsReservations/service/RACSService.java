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
import com.FlightsReservations.domain.dto.UpdateRACSDTO;
import com.FlightsReservations.repository.RACSRepository;

@Component
@Transactional(readOnly = true)
public class RACSService {

	@Autowired
	RACSRepository racsRepository;
	
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
		
		return racsRepository.save(t);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean update(UpdateRACSDTO t) {
		RACS r = findOne(t.getId());
		if (r != null) {
			r.setLongitude(t.getLongitude());
			r.setLatitude(t.getLatitude());
			r.setPromoDescription(t.getPromoDescription());
			r.setName(t.getName());
			r.setAverageScore(t.getAverageScore());
			r.setNumberOfVotes(t.getNumberOfVotes());
			racsRepository.save(r);
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
			racsRepository.save(racs);
			return true;
		}
		return false;
	}
	

	public RACS findOne(Long id) {
		try {
			return racsRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public Collection<RACS> findByName(String name) {
		try {
			return racsRepository.findByName(name);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		racsRepository.deleteById(id);
	}

	public Collection<RACS> findAll() {
		return racsRepository.findAll();
	}
}
