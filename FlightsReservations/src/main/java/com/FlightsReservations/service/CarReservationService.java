package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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

		Date startTime = dto.getStartTime();
		Date endTime = dto.getEndTime();
		
		int reservationDurationHours = (int) ( (endTime.getTime() - startTime.getTime() ) / 3600000 );
		User owner = userRepository.findByEmail(dto.getOwnerEmail());
		Car car = carRepository.findById(dto.getCarId()).get();
		Float total = (float) car.getPricePerHour() * reservationDurationHours;
		Float discount = dto.getDiscount();
		
		total = total - total*discount / 100;
		
		CarReservation reservation = new CarReservation(new Date(), discount, total, (Boolean) true, owner, dto.getCarId(), startTime, endTime);
		
		reservation = repository.save(reservation);
		return new CarReservationDTO(reservation);
	}
	
	private boolean creatingSemanticValidation(CarReservationRequestDTO dto) {
		// user with given email must exist
		if (userRepository.findByEmail(dto.getOwnerEmail()) == null)
			return false;
		
		// car with given id must exist 
		if (carRepository.findById(dto.getCarId()) == null)
			return false;

		Date startTime = dto.getStartTime();
		Date endTime = dto.getEndTime();
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(startTime);
		
		// only on the hour (= top of the hour) times are accepted, e.g. 08:00, 12:00 etc.
		if (!(calendar.get(Calendar.MINUTE) == 0) ) 
			return false;
		
		calendar.setTime(endTime);

		if (!(calendar.get(Calendar.MINUTE) == 0) ) 
			return false;
		
		// start time has to be before end time
		if (startTime.after(endTime))
			return false;
		
		// car can't be rented for more than 7 days
		long diff = endTime.getTime() - startTime.getTime();
		long diffInDays = diff / (1000 * 60 * 60 * 24);
		if (diffInDays > 7)
			return false;
		
		//TODO: check that there are no other reservations for car id via transactions		
		return true;
	}
	
	public static Date isValidFormat(String format, String value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            date = sdf.parse(value);
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return date;
    }
}
