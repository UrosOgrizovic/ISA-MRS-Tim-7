package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.Discount;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.dto.CarDTO;
import com.FlightsReservations.repository.CarReservationRepository;
import com.FlightsReservations.repository.RACSRepository;

@Component
public class RACSService {

	@Autowired
	RACSRepository repository;
	
	@Autowired
	CarReservationRepository carReservationRepository;
	

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

	public boolean update(RACS t) {
		RACS r = findOne(t.getId());
		if (r != null) {
			r.setLongitude(t.getLongitude());
			r.setLatitude(t.getLatitude());
			r.setBranchOffices(t.getBranchOffices());
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
					car.getPricePerHour(),
					car.getAverageRating(),
					car.getNumberOfVotes());

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

	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Collection<RACS> findAll() {
		return repository.findAll();
	}

	public double getRevenueForPeriod(RACS racs, String startTime, String endTime) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date startDate = new Date();
		Date endDate = new Date();
		try {
			startDate = sdf.parse(startTime);
			endDate = sdf.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		double revenue = 0;
		Collection<CarReservation> carReservations = getCarReservationsOfRacs(racs.getId());
		for (CarReservation cr : carReservations) {
			if (cr.getStartTime().compareTo(startDate) >= 0 && cr.getEndTime().compareTo(endDate) <= 0) {
				revenue += cr.getPrice();
			}
		}
		return revenue;
	}
	
	public Collection<CarReservation> getCarReservationsOfRacs(Long id) {
		return carReservationRepository.findCarReservationsOfRacs(id);
	}
}
