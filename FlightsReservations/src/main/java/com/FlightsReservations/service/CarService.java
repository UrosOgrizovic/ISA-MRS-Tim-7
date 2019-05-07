package com.FlightsReservations.service;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.repository.CarRepository;

@Component
public class CarService {
	@Autowired
	CarRepository repository;

	public Car create(Car t) {
		return repository.save(t);
	}
	
	public boolean update(Car c) {
		Car car = findOne(c.getId());
		if (car != null) {
			car.setColor(c.getColor());
			car.setManufacturer(c.getManufacturer());
			car.setYearOfManufacture(c.getYearOfManufacture());
			car.setName(c.getName());
			car.setPricePerHour(c.getPricePerHour());
			repository.save(car);
			return true;
		}
		return false;
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}

	public Car findOne(Long carID) {
		try {
			return repository.findById(carID).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public Collection<Car> findAll() {
		return repository.findAll();
	}
	
}
