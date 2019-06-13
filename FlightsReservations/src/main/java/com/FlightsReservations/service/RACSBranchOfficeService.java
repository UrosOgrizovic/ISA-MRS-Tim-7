package com.FlightsReservations.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.Discount;
import com.FlightsReservations.domain.RACSBranchOffice;
import com.FlightsReservations.domain.dto.CarDTO;
import com.FlightsReservations.repository.RACSBranchOfficeRepository;

@Component
public class RACSBranchOfficeService {
	@Autowired
	RACSBranchOfficeRepository racsBranchOfficeRepository;
	
	public RACSBranchOffice create(RACSBranchOffice t) {
		Set<Car> cars = t.getCars();
		if (cars != null) {
			for (Car c : cars) {
				if (c.getDiscounts() == null)
					c.setDiscounts(new HashSet<Discount>());
			}
		}
		return	racsBranchOfficeRepository.save(t);
	}
	
	public boolean update(RACSBranchOffice t) {
		RACSBranchOffice r = findOne(t.getId());
		if (r != null) {
			r.setLongitude(t.getLongitude());
			r.setLatitude(t.getLatitude());
			r.setCars(t.getCars());
			r.setName(t.getName());
			racsBranchOfficeRepository.save(r);
			return true;
		}
		return false;
	}
	
	public boolean addCar(CarDTO car) {
		RACSBranchOffice racsBranchOffice = findOne(car.getRACSBranchOfficeId());
		
		if (racsBranchOffice != null) {
			Car c = new Car(
					car.getManufacturer(), 
					car.getName(), 
					car.getYearOfManufacture(), 
					car.getColor(), 
					racsBranchOffice,
					car.getPricePerHour(),
					car.getAverageScore(),
					car.getNumberOfVotes());

			racsBranchOffice.getCars().add(c);
			racsBranchOfficeRepository.save(racsBranchOffice);
			return true;
		}
		return false;
	}
	
	public RACSBranchOffice findOne(Long id) {
		try {
			return racsBranchOfficeRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public Collection<RACSBranchOffice> findByName(String name) {
		try {
			return racsBranchOfficeRepository.findByName(name);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public void delete(Long id) {
		racsBranchOfficeRepository.deleteById(id);
	}

	public Collection<RACSBranchOffice> findAll() {
		return racsBranchOfficeRepository.findAll();
	}

}
