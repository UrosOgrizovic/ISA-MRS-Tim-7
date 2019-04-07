package com.FlightsReservations.service;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.dto.CarDTO;
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
			// r.setBranchOffices(t.getBranchOffices());
			r.setCars(t.getCars());
			r.setDescription(t.getDescription());
			r.setName(t.getName());
			// r.setPricelist(t.getPricelist());
			for (Car c : r.getCars()) {
				System.out.println(c.getManufacturer() + " " + c.getName() + " " + c.getColor());
			}
			repository.save(r);
			return true;
		}
		return false;
	}

	public boolean addCar(CarDTO car) {
		Long racsID = car.getRacsID();
		RACS racs = findOne(racsID);

		if (racs != null) {
			Car c = new Car(
					car.getManufacturer(), 
					car.getName(), 
					car.getYearOfManufacture(), 
					car.getColor(), 
					racs);

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

	public void delete(Long id) {
		// TODO Auto-generated method stub

	}

	public Collection<RACS> findAll() {
		return repository.findAll();
	}

}
