package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.AbstractUser;
import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.dto.CarReservationDTO;
import com.FlightsReservations.domain.dto.CarReservationRequestDTO;
import com.FlightsReservations.repository.AbstractUserRepository;
import com.FlightsReservations.repository.CarRepository;
import com.FlightsReservations.repository.CarReservationRepository;

@Service
public class CarReservationService {
	@Autowired
	private CarReservationRepository repository;
	
	@Autowired
	private AbstractUserRepository abstractUserRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	public CarReservationDTO create(CarReservationRequestDTO dto) {
		if (!creatingSemanticValidation(dto))
			return null;

		Date startTime = dto.getStartTime();
		Date endTime = dto.getEndTime();
		
		int reservationDurationHours = (int) ( (endTime.getTime() - startTime.getTime() ) / 3600000 );
		AbstractUser owner = abstractUserRepository.findByEmail(dto.getOwnerEmail());
		Car car = carRepository.findById(dto.getCarId()).get();
		Float total = (float) car.getPricePerHour() * reservationDurationHours;
		
		float discount = car.getDiscountValueForPeriod(dto.getStartTime(), dto.getEndTime()); 
		if (discount > 0) {
			total = total - total * discount/100;
		}
		
		CarReservation reservation = new CarReservation(new Date(), total, (Boolean) true, owner, dto.getCarId(), startTime, endTime, car.getRACSBranchOffice().getId());
		reservation.getCompanyRating().setReservationId(reservation.getId());
		reservation.getCompanyRating().setName(car.getRACSBranchOffice().getName());
		
		reservation = repository.save(reservation);
		return new CarReservationDTO(reservation);
	}
	
	private boolean creatingSemanticValidation(CarReservationRequestDTO dto) {
		// user with given email must exist
		if (abstractUserRepository.findByEmail(dto.getOwnerEmail()) == null)
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
	
	public boolean cancel(Long id) {
		CarReservation cr = repository.findById(id).get();
		if (cr != null) {
			Date now = new Date();
			// difference between now (cancellation time) and reservation start time in days
			long diff = (cr.getStartTime().getTime() - now.getTime()) / (24 * 60 * 60 * 1000);
			// reservation cannot be cancelled less than two days before the reservation starts
			if (diff < 2) return false;
			cr.setConfirmed(false);
			repository.save(cr);
			return true;
		}
		return false;
	}
}
