package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.Discount;
import com.FlightsReservations.domain.dto.CreateCarDiscountDTO;
import com.FlightsReservations.domain.dto.DiscountCarDTO;
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
	
	public String addDiscountToCar(CreateCarDiscountDTO discount) {
		if (discount.getStartTime().after(discount.getEndTime()) || discount.getStartTime().compareTo(discount.getEndTime()) == 0)
			return "Start time of discount cannot be equal to or after end time of discount";
		Car car = repository.getOne(discount.getCarId());
		boolean discountsExistForPeriod = car.checkIfAnyDiscountsForPeriod(discount.getStartTime(), discount.getEndTime());
		// if no discount
		if (!discountsExistForPeriod) {
			Discount d = new Discount(discount.getStartTime(), discount.getEndTime(), discount.getDiscountValue());
			Set<Discount> carDiscounts = car.getDiscounts();
			carDiscounts.add(d);
			repository.save(car);
			return "Success";
		}
		return "Car already has discount for given period";
			
	}
	
	public Collection<DiscountCarDTO> getAllDiscountCarsForPeriod(String startTime, String endTime) {
		Collection<Car> cars = repository.findAll();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date startDate = new Date();
		Date endDate = new Date();
		try {
			startDate = sdf.parse(startTime);
			endDate = sdf.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		ArrayList<DiscountCarDTO> discountCars = new ArrayList<DiscountCarDTO>();
		for (Car c : cars) {
			Set<Discount> carDiscounts = c.getDiscounts();
			for (Discount d : carDiscounts) {
				if (! (endDate.before(d.getStartTime()) && startDate.after(d.getEndTime()))) {
					
				
				
					discountCars.add(new DiscountCarDTO(c));
				}
			}
			
		}
		return discountCars;
	}
	
}
