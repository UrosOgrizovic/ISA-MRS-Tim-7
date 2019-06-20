package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.FlightsReservations.domain.AbstractUser;
import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.Discount;
import com.FlightsReservations.domain.Rating;
import com.FlightsReservations.domain.dto.CarReservationDTO;
import com.FlightsReservations.domain.dto.CarReservationRequestDTO;
import com.FlightsReservations.repository.AbstractUserRepository;
import com.FlightsReservations.repository.CarRepository;
import com.FlightsReservations.repository.CarReservationRepository;

@Service
@Transactional(readOnly = true)
public class CarReservationService {
	@Autowired
	private CarReservationRepository carReservationRepository;
	
	@Autowired
	private AbstractUserRepository abstractUserRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Transactional(readOnly = false)
	public String create(CarReservationRequestDTO dto) {
		String toReturn = creatingSemanticValidation(dto); 
		if (!toReturn.equalsIgnoreCase("success"))
			return toReturn;
		
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
		reservation.setRating(new Rating());
		reservation.getRating().setReservation(reservation);
		reservation.getRating().setRacsBranchOfficeId(car.getRACSBranchOffice().getId());
		reservation = carReservationRepository.save(reservation);
		return "Reservation successful";
	}
	
	/**
	 * 
	 * @param dto
	 * @return String indicating whether input is good
	 */
	private String creatingSemanticValidation(CarReservationRequestDTO dto) {
		
		// user with given email must exist
		if (abstractUserRepository.findByEmail(dto.getOwnerEmail()) == null)
			return "User with given email doesn't exist";
		
		// car with given id must exist 
		if (carRepository.findById(dto.getCarId()) == null)
			return "Car with given id doesn't exist";

		Date reservationStartTime = dto.getStartTime();
		Date reservationEndTime = dto.getEndTime();
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(reservationStartTime);
		
		// only on the hour (= top of the hour) times are accepted, e.g. 08:00, 12:00 etc.
		if (!(calendar.get(Calendar.MINUTE) == 0) ) 
			return "Only on the hour times are accepted for reservation start time, e.g. 08:00, 12:00 etc.";
		
		calendar.setTime(reservationEndTime);

		if (!(calendar.get(Calendar.MINUTE) == 0) ) 
			return "Only on the hour times are accepted for reservation end time, e.g. 08:00, 12:00 etc.";
		
		// start time has to be before end time
		if (reservationStartTime.after(reservationEndTime))
			return "Reservation start time has to be before reservation end time";
		
		// car can't be rented for more than 7 days
		long diff = reservationEndTime.getTime() - reservationStartTime.getTime();
		long diffInDays = diff / (1000 * 60 * 60 * 24);
		if (diffInDays > 7)
			return "Car can't be rented for more than 7 days";
		
		// no other reservations may exist for selected car and entered period
		ArrayList<CarReservation> carReservationsForPeriod = (ArrayList<CarReservation>) carReservationRepository.findCarReservationsForPeriod(dto.getStartTime(), dto.getEndTime());
		if (carReservationsForPeriod != null && carReservationsForPeriod.size() > 0) {
			return "Car is already reserved in selected period";
		}
		
		if (!dto.isFastReservation()) {
			Car c = carRepository.getOne(dto.getCarId());
			Set<Discount> discounts = c.getDiscounts();
			for (Discount d : discounts) {
				// car can't be available for a regular reservation and a fast reservation at the same time
				if ( (reservationStartTime.compareTo(d.getStartTime()) >= 0 && reservationStartTime.compareTo(d.getEndTime()) <= 0) || 
						(reservationEndTime.compareTo(d.getStartTime()) >= 0 && reservationEndTime.compareTo(d.getEndTime()) <= 0)) {
					return "Car is on discount during that period, so it can't be reserved the regular way";
				}
			}
		}
		
		
		return "success";
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
	
	@Transactional(readOnly = false)
	public boolean cancel(Long id) {
		CarReservation cr = carReservationRepository.findById(id).get();
		if (cr != null) {
			Date now = new Date();
			// difference between now (cancellation time) and reservation start time in days
			long diff = (cr.getStartTime().getTime() - now.getTime()) / (24 * 60 * 60 * 1000);
			// reservation cannot be cancelled less than two days before the reservation starts
			if (diff < 2) return false;
			cr.setConfirmed(false);
			carReservationRepository.save(cr);
			return true;
		}
		return false;
	}
}