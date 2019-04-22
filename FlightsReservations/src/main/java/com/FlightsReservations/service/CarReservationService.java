package com.FlightsReservations.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.CarReservationDTO;
import com.FlightsReservations.domain.dto.CarReservationRequestDTO;
import com.FlightsReservations.repository.CarRepository;
import com.FlightsReservations.repository.CarReservationRepository;
import com.FlightsReservations.repository.UserRepository;

@Service
public class CarReservationService {
	@Autowired
	private CarReservationRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	public CarReservationDTO create(CarReservationRequestDTO dto) {
		if (!creatingSemanticValidation(dto))
			return null;

		User owner = userRepository.findByEmail(dto.getOwnerEmail());
		Car car = carRepository.findById(dto.getCarId()).get();
		Float total = (float) car.getPricePerHour() * dto.getReservationDurationHours();
		Float discount = (float) 0;
		CarReservation reservation = new CarReservation(new Date(), discount, total, (Boolean) true, owner, dto.getCarId(), dto.getReservationDurationHours());
		
		reservation = repository.save(reservation);
		return new CarReservationDTO(reservation);
	}
	
	private boolean creatingSemanticValidation(CarReservationRequestDTO dto) {
		// user with given email must exist
		if (userRepository.findByEmail(dto.getOwnerEmail()) == null)
			return false;
		
		// car id must exist 
		if (carRepository.findById(dto.getCarId()) == null)
			return false;
		
		// car can't be rented for more than 7 days
		if (dto.getReservationDurationHours() > (7 * 24) )
			return false;
		
		//TODO: check that there are no other reservations for car id via transactions		
		return true;
	}
}
