package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.Discount;
import com.FlightsReservations.domain.dto.CarRatingDTO;
import com.FlightsReservations.domain.dto.CreateCarDiscountDTO;
import com.FlightsReservations.domain.dto.DiscountCarDTO;
import com.FlightsReservations.repository.CarRepository;

@Component
@Transactional(readOnly = true)
public class CarService {
	@Autowired
	CarRepository carRepository;

	@Transactional(readOnly = false)
	public Car create(Car t) {
		return carRepository.save(t);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean update(Car c) {
		Car car = findOne(c.getId());
		if (car != null) {
			
			car.setColor(c.getColor());
			car.setManufacturer(c.getManufacturer());
			car.setYearOfManufacture(c.getYearOfManufacture());
			car.setName(c.getName());
			car.setPricePerHour(c.getPricePerHour());
			carRepository.save(car);
			// avoiding lost update problem
			try {
				carRepository.flush();
			} catch (OptimisticLockingFailureException e) {
				throw new OptimisticLockingFailureException("Car already updated");
			}
			return true;
		}
		return false;
	}
	
	@Transactional(readOnly = false)
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
	
	@Transactional(readOnly = false)
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
	
	public Collection<DiscountCarDTO> getAllDiscountCarsForPeriod(String startTime, String endTime) {
		Collection<Car> cars = carRepository.findAll();
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
		double pph = 0;
		Date dtoStartTime = new Date();
		Date dtoEndTime = new Date();
		ArrayList<DiscountCarDTO> discountCars = new ArrayList<DiscountCarDTO>();
		for (Car c : cars) {
			totalPrice = 0;
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
					
					
					discountCars.add(new DiscountCarDTO(c, totalPrice, dtoStartTime, dtoEndTime));
				}
			}
			
		}
		return discountCars;
	}
	
	public CarRatingDTO rate(CarRatingDTO dto) {
		Car c = carRepository.findById(dto.getCarId()).get();
		if (c != null) {
			float newAvgScore = c.getAverageScore() * c.getNumberOfVotes() + dto.getAverageScore();
			int newNumberOfVotes = c.getNumberOfVotes() + 1;
			c.setNumberOfVotes(newNumberOfVotes);
			c.setAverageScore(newAvgScore / newNumberOfVotes);
			carRepository.save(c);
			return new CarRatingDTO(c.getAverageScore(), c.getId());
		}
		return null;
	}
	
}