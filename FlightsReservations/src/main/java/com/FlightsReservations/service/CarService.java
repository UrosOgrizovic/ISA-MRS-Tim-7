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
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.dto.CreateCarDiscountDTO;
import com.FlightsReservations.domain.dto.DiscountCarDTO;
import com.FlightsReservations.repository.CarRepository;
import com.FlightsReservations.repository.RACSRepository;

@Component
public class CarService {
	@Autowired
	CarRepository carRepository;
	
	@Autowired
	RACSRepository racsRepository;

	public Car create(Car t) {
		return carRepository.save(t);
	}
	
	public boolean update(Car c) {
		Car car = findOne(c.getId());
		if (car != null) {
			car.setColor(c.getColor());
			car.setManufacturer(c.getManufacturer());
			car.setYearOfManufacture(c.getYearOfManufacture());
			car.setName(c.getName());
			car.setPricePerHour(c.getPricePerHour());
			carRepository.save(car);
			return true;
		}
		return false;
	}
	
	public void delete(Long id) {
		carRepository.deleteById(id);
	}

	public Car findOne(Long carID) {
		try {
			return carRepository.findById(carID).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public Collection<Car> findAll() {
		return carRepository.findAll();
	}
	
	public String addDiscountToCar(CreateCarDiscountDTO discount) {
		if (discount.getStartTime().after(discount.getEndTime()) || discount.getStartTime().compareTo(discount.getEndTime()) == 0)
			return "Start time of discount cannot be equal to or after end time of discount";
		Car car = carRepository.getOne(discount.getCarId());
		boolean discountsExistForPeriod = car.checkIfAnyDiscountsForPeriod(discount.getStartTime(), discount.getEndTime());
		// if no discount
		if (!discountsExistForPeriod) {
			Discount d = new Discount(discount.getStartTime(), discount.getEndTime(), discount.getDiscountValue());
			Set<Discount> carDiscounts = car.getDiscounts();
			carDiscounts.add(d);
			carRepository.save(car);
			return "Success";
		}
		return "Car already has discount for given period";
			
	}
	
	public Collection<DiscountCarDTO> getAllDiscountCarsForPeriod(String startTime, String endTime, String city) {
		Collection<Car> cars = getAllCarsForCity(city);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		Date startDate = new Date();
		Date endDate = new Date();
		try {
			startDate = sdf.parse(startTime);
			endDate = sdf.parse(endTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		double totalPrice = 0;
		double initialPrice = 0;
		double pph = 0;
		Date dtoStartTime = new Date();
		Date dtoEndTime = new Date();
		ArrayList<DiscountCarDTO> discountCars = new ArrayList<DiscountCarDTO>();
		for (Car c : cars) {
			totalPrice = 0;
			initialPrice = 0;
			pph = c.getPricePerHour();
			Set<Discount> carDiscounts = c.getDiscounts();
			for (Discount d : carDiscounts) {
				if (! (endDate.before(d.getStartTime()) && startDate.after(d.getEndTime()))) {
					// after, after
					if (d.getStartTime().after(startDate) && d.getEndTime().after(endDate)) {
						dtoStartTime = d.getStartTime();
						dtoEndTime = endDate;
					// after, before
					} else if (d.getStartTime().after(startDate) && d.getEndTime().before(endDate)) {
						dtoStartTime = d.getStartTime();
						dtoEndTime = d.getEndTime();
					// before, after
					} else if (d.getStartTime().before(startDate) && d.getEndTime().after(endDate)) {
						dtoStartTime = startDate;
						dtoEndTime = endDate;
					// before, before
					} else {
						dtoStartTime = startDate;
						dtoEndTime = d.getEndTime();
					}
					int hoursBetween = (int) (dtoEndTime.getTime() - dtoStartTime.getTime()) / 3600000;
					double newPPH = pph - pph * d.getDiscountValue() / 100;
					totalPrice += newPPH * hoursBetween;
					initialPrice = pph * hoursBetween;
					
					
					discountCars.add(new DiscountCarDTO(c, totalPrice, dtoStartTime, dtoEndTime, initialPrice, d.getDiscountValue()));
				}
			}
			
		}
		return discountCars;
	}

	private Collection<Car> getAllCarsForCity(String city) {
		Collection<RACS> racsForCity = racsRepository.findByCity(city);
		Collection<Car> carsForCity = new ArrayList<Car>();
		
		for (RACS racs : racsForCity) {
			Set<Car> carsOfRacs = racs.getCars();
			for (Car c : carsOfRacs) {
				carsForCity.add(c);
			}
		}
		return carsForCity;
		
	}
	
}
