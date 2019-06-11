package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Email;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.Discount;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.RACSAdmin;
import com.FlightsReservations.domain.dto.CarDTO;
import com.FlightsReservations.domain.dto.RACSAdminDTO;
import com.FlightsReservations.repository.CarReservationRepository;
import com.FlightsReservations.repository.RACSAdminRepository;
import com.FlightsReservations.repository.RACSRepository;

@Component
@Transactional(readOnly = false)
public class RACSService {

	@Autowired
	RACSRepository repository;
	
	@Autowired
	CarReservationRepository carReservationRepository;
	
	@Autowired
	RACSAdminRepository racsAdminRepository;

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
			r.setAdmin(t.getAdmin());
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

	public HashMap<String, Float> getRevenueForPeriod(Long racsId, String startTime, String endTime) {
		HashMap<String, Float> dayRevenue = new HashMap<String, Float>();
		
		Date startDate = parseDate(startTime);
		Date endDate = parseDate(endTime);
		
		if (startDate == null || endDate == null) return null;
		
		DateTime dt = new DateTime(startDate);
		
		String moy = String.valueOf(dt.getMonthOfYear());
		if (moy.length() == 1) moy = "0" + moy;
		Date currentDay = parseDate(dt.getDayOfMonth() + "-" + moy + "-" + dt.getYear() + " 00:00");
		System.out.println(currentDay);
		
		float revenueForCurrentDay;
		Collection<CarReservation> carReservations = getCarReservationsOfRacs(racsId);
		while (currentDay.compareTo(endDate) < 0) {
			moy = String.valueOf(dt.getMonthOfYear());
			if (moy.length() == 1) moy = "0" + moy;
			currentDay = parseDate(dt.getDayOfMonth() + "-" + moy + "-" + dt.getYear() + " 00:00");
			
			revenueForCurrentDay = 0;
			for (CarReservation cr : carReservations) {
				// if date of reservation between current day and the day after
				if (cr.getDateOfReservation().compareTo(currentDay) >= 0 && cr.getDateOfReservation().compareTo(addDays(currentDay, 1)) < 0) {
					revenueForCurrentDay += cr.getPrice();
					
				}
			}
			
			String currentDayAsString = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(currentDay);
			currentDayAsString = currentDayAsString.substring(0, 10);
			dayRevenue.put(currentDayAsString, revenueForCurrentDay);
			dt = dt.plusDays(1);
		}
		
		return dayRevenue;
	}
	
	public Collection<CarReservation> getCarReservationsOfRacs(Long id) {
		return carReservationRepository.findCarReservationsOfRacs(id);
	}
	
	// <String, Integer> = <date of reservation, number of reservations for date>
	public HashMap<String, Integer> getNumberOfCarReservationsOfRacs(Long racsId, String startTime, String endTime, String unit) {
		HashMap<String, Integer> dateNumberOfReservations = new HashMap<String, Integer>();
		Collection<CarReservation> carReservations = getCarReservationsOfRacs(racsId);
		Date startDate = parseDate(startTime);
		Date endDate = parseDate(endTime);
		
		if (startDate == null || endDate == null) return null;
		
		DateTime dt = new DateTime(startDate);
		String moy = String.valueOf(dt.getMonthOfYear());
		if (moy.length() == 1) moy = "0" + moy;
		Date currentDay = parseDate(dt.getDayOfMonth() + "-" + moy + "-" + dt.getYear() + " 00:00");
		
		int currentDayNumberOfCarReservations;
		while (currentDay.compareTo(endDate) < 0) {
			moy = String.valueOf(dt.getMonthOfYear());
			if (moy.length() == 1) moy = "0" + moy;
			currentDay = parseDate(dt.getDayOfMonth() + "-" + moy + "-" + dt.getYear() + " 00:00");
			currentDayNumberOfCarReservations = 0;
			for (CarReservation cr : carReservations) {
				if (cr.getDateOfReservation().compareTo(currentDay) >= 0 && cr.getDateOfReservation().compareTo(addDays(currentDay, 1)) < 0) {
					currentDayNumberOfCarReservations++;
				}
			}
			
			String currentDayAsString = new SimpleDateFormat("dd-MM-yyyy HH:mm").format(currentDay);
			currentDayAsString = currentDayAsString.substring(0, 10);
			
			dateNumberOfReservations.put(currentDayAsString, currentDayNumberOfCarReservations);
			if (unit.equalsIgnoreCase("day")) dt = dt.plusDays(1);
			else if (unit.equalsIgnoreCase("week")) dt = dt.plusWeeks(1);
			else if (unit.equalsIgnoreCase("month")) dt = dt.plusWeeks(4);
		}
		
		return dateNumberOfReservations;
	}
	
	
	public Date parseDate(String time) {
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
		
		try {
			return sdf.parse(time);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Date addDays(Date date, int days) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, days); //minus number would decrement the days
        return cal.getTime();
    }
	
	public Date addHours(Date date, int hours) {
		Calendar cal = Calendar.getInstance(); 
	    cal.setTime(date);
	    cal.add(Calendar.HOUR_OF_DAY, hours); 
	    return cal.getTime(); 
	}
	
	// returns difference between default time zone and UTC
	public static long getCurrentTimeZoneOffsetInHours() {
	    DateTimeZone tz = DateTimeZone.getDefault();
	    Long instant = DateTime.now().getMillis();

	    long offsetInMilliseconds = tz.getOffset(instant);
	    long hours = TimeUnit.MILLISECONDS.toHours( offsetInMilliseconds );

	    return hours;
	}

	// add RACSAdmin to RACS
	public RACSAdminDTO addAdmin(Long racsId, @Email String email) {
		RACS racs = findOne(racsId);
		RACSAdmin racsAdmin = racsAdminRepository.findByEmail(email);
		if (racs != null && racsAdmin != null) {
			
				
			racsAdmin.setRACS(racs);
			
			racs.setAdmin(racsAdmin);
			repository.save(racs);
			return new RACSAdminDTO(racsAdmin);
				
		}
		
		return null;
	}

	// <String, Float> - <car id: car manufacturer car name, average rating>
	public HashMap<String, Float> getAverageRatingForEachCarOfRacs(RACS racs) {
		Set<Car> cars = racs.getCars();
		HashMap<String, Float> carNameAverageRating = new HashMap<String, Float>();
		for (Car c : cars) {
			carNameAverageRating.put("#" + c.getId() + ": " + c.getManufacturer() + " " + c.getName(), c.getAverageRating());
		}
		return carNameAverageRating;
	}


}
