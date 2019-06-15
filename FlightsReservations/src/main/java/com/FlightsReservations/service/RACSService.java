package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.validation.constraints.Email;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.FlightsReservations.domain.BranchOffice;
import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.RACSAdmin;
import com.FlightsReservations.domain.RACSBranchOffice;
import com.FlightsReservations.domain.RACSPricelistItem;
import com.FlightsReservations.domain.dto.CarDTO;
import com.FlightsReservations.domain.dto.UpdateRACSDTO;
import com.FlightsReservations.repository.RACSRepository;
import com.FlightsReservations.domain.dto.RACSAdminDTO;
import com.FlightsReservations.domain.dto.RACSBranchOfficeDTO;
import com.FlightsReservations.domain.dto.RACSDTO;
import com.FlightsReservations.domain.dto.RACSPricelistItemDTO;
import com.FlightsReservations.repository.CarReservationRepository;
import com.FlightsReservations.repository.CompanyRepository;
import com.FlightsReservations.repository.RACSAdminRepository;
import com.FlightsReservations.repository.RACSBranchOfficeRepository;

@Component
@Transactional(readOnly = false)
public class RACSService {

	@Autowired
	RACSRepository racsRepository;
	
	@Autowired
	RACSBranchOfficeRepository racsBranchOfficeRepository;
	
	@Autowired
	CompanyRepository companyRepository;


	@Autowired
	CarReservationRepository carReservationRepository;
	
	@Autowired
	RACSAdminRepository racsAdminRepository;

	@Transactional(readOnly = false)
	public RACSDTO create(RACSDTO t) {
		RACS r = racsRepository.findByName(t.getName());
		if (r == null) {
			r = new RACS();
			r.setAverageScore(t.getAverageScore());
			Set<BranchOffice> branchOffices = new HashSet<BranchOffice>();
			Set<RACSBranchOfficeDTO> bodtos = t.getBranchOffices();
			for (RACSBranchOfficeDTO bodto : bodtos) {
				RACSBranchOffice bo = new RACSBranchOffice();
				bo.setCompany(companyRepository.findByName(bodto.getRACSCompanyName()));
				Set<CarDTO> cdtos = bodto.getCars();
				Set<Car> cars = new HashSet<Car>();
				for (CarDTO cdto : cdtos) {
					Car car = new Car();
					car.setAverageScore(cdto.getAverageScore());
					car.setColor(cdto.getColor());
					car.setDiscounts(cdto.getDiscounts());
					car.setManufacturer(cdto.getManufacturer());
					car.setName(cdto.getName());
					car.setNumberOfVotes(cdto.getNumberOfVotes());
					car.setPricePerHour(cdto.getPricePerHour());
					car.setRACSBranchOffice(racsBranchOfficeRepository.findByName(cdto.getRacsBranchOfficeName()));
					car.setYearOfManufacture(cdto.getYearOfManufacture());
					cars.add(car);
				}
				bo.setCars(cars);
				bo.setLatitude(bodto.getLatitude());
				bo.setLongitude(bodto.getLongitude());
				bo.setName(bodto.getName());
				branchOffices.add(bo);
			}
			r.setBranchOffices(branchOffices);
			r.setCity(t.getCity());
			r.setLatitude(t.getLatitude());
			r.setLongitude(t.getLongitude());
			r.setName(t.getName());
			r.setNumberOfVotes(t.getNumberOfVotes());
			Set<RACSPricelistItemDTO> plidtos = t.getPricelist();
			Set<RACSPricelistItem> pricelist = new HashSet<RACSPricelistItem>();
			for (RACSPricelistItemDTO plidto : plidtos) {
				RACSPricelistItem pli = new RACSPricelistItem();
				pli.setName(plidto.getName());
				pli.setPrice(plidto.getPrice());
				pli.setRacs(r);
				pricelist.add(pli);
			}
			r.setPricelist(pricelist);
			r.setPromoDescription(t.getPromoDescription());
			r.setState(t.getState());
			
			racsRepository.save(r);
			return new RACSDTO(r);
		}
		return null;
		
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public boolean update(UpdateRACSDTO t) {
		RACS r = racsRepository.findByName(t.getName());
		if (r != null) {
			r.setLongitude(t.getLongitude());
			r.setLatitude(t.getLatitude());
			r.setPromoDescription(t.getPromoDescription());
			r.setName(t.getName());
			r.setAverageScore(t.getAverageScore());
			r.setNumberOfVotes(t.getNumberOfVotes());
			racsRepository.save(r);
			return true;
		}
		return false;
	}
	
	@Transactional(readOnly = false)
	public boolean addCar(CarDTO car) {
		RACSBranchOffice rbo = racsBranchOfficeRepository.findByName(car.getRacsBranchOfficeName());
		
		if (rbo != null) {
			
			Car c = new Car(
					car.getManufacturer(), 
					car.getName(), 
					car.getYearOfManufacture(), 
					car.getColor(), 
					rbo,
					car.getPricePerHour(),
					car.getAverageScore(),
					car.getNumberOfVotes());

			rbo.getCars().add(c);
			
			racsBranchOfficeRepository.save(rbo);
			return true;
		}
		return false;
	}
	

	public RACSDTO findOne(String name) {
		RACS r = racsRepository.findByName(name);
		if (r != null) 
			return new RACSDTO(r);
		return null;
	}
	
	
	@Transactional(readOnly = false)
	public void delete(Long id) {
		racsRepository.deleteById(id);
	}

	public List<RACSDTO> findAll() {
		List<RACSDTO> dtos = new ArrayList<RACSDTO>();
		for (RACS r : racsRepository.findAll()) {
			dtos.add(new RACSDTO(r));
		}
		return dtos;
	}

	public ArrayList<Car> searchAllCars(Collection<RACSDTO> racss, String name, String manufacturer, int yearOfManufacture) {
		ArrayList<Car> matchingCars = new ArrayList<Car>();
		Set<RACSBranchOffice> branchOfficesOfRACS = new HashSet<RACSBranchOffice>();
		Set<RACSBranchOfficeDTO> branchOffices = new HashSet<RACSBranchOfficeDTO>();
		if (yearOfManufacture != 0) {
			if (name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACSDTO r : racss) {
					branchOffices = r.getBranchOffices();
					
					RACSBranchOffice racsBranchOffice = new RACSBranchOffice();
					for (RACSBranchOfficeDTO bo : branchOffices) {
						if (bo instanceof RACSBranchOfficeDTO) {
							Set<Car> cars = new HashSet<Car>();
							Set<CarDTO> cardtos = bo.getCars();
							for (CarDTO cdto : cardtos) {
								Car c = new Car();
								c.setAverageScore(cdto.getAverageScore());
								c.setColor(cdto.getColor());
								c.setDiscounts(cdto.getDiscounts());
								c.setManufacturer(cdto.getManufacturer());
								c.setName(cdto.getName());
								c.setNumberOfVotes(cdto.getNumberOfVotes());
								c.setPricePerHour(cdto.getPricePerHour());
								c.setRACSBranchOffice(racsBranchOfficeRepository.findByName(cdto.getRacsBranchOfficeName()));
								c.setYearOfManufacture(cdto.getYearOfManufacture());
								cars.add(c);
							}
							racsBranchOffice.setCars(cars);
							racsBranchOffice.setCompany(racsRepository.findByName(bo.getRACSCompanyName()));
							racsBranchOffice.setLatitude(bo.getLatitude());
							racsBranchOffice.setLongitude(bo.getLongitude());
							racsBranchOffice.setName(bo.getName());
							
							branchOfficesOfRACS.add(racsBranchOffice);
						}
					}
					
					
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (yearOfManufacture == c.getYearOfManufacture()) {
								matchingCars.add(c);
							}
						}

					}
				}
			} else if (name.trim().isEmpty() && !manufacturer.trim().isEmpty()) {
				for (RACSDTO r : racss) {
					branchOffices = r.getBranchOffices();
					
					RACSBranchOffice racsBranchOffice = new RACSBranchOffice();
					for (RACSBranchOfficeDTO bo : branchOffices) {
						if (bo instanceof RACSBranchOfficeDTO) {
							Set<Car> cars = new HashSet<Car>();
							Set<CarDTO> cardtos = bo.getCars();
							for (CarDTO cdto : cardtos) {
								Car c = new Car();
								c.setAverageScore(cdto.getAverageScore());
								c.setColor(cdto.getColor());
								c.setDiscounts(cdto.getDiscounts());
								c.setManufacturer(cdto.getManufacturer());
								c.setName(cdto.getName());
								c.setNumberOfVotes(cdto.getNumberOfVotes());
								c.setPricePerHour(cdto.getPricePerHour());
								c.setRACSBranchOffice(racsBranchOfficeRepository.findByName(cdto.getRacsBranchOfficeName()));
								c.setYearOfManufacture(cdto.getYearOfManufacture());
								cars.add(c);
							}
							racsBranchOffice.setCars(cars);
							racsBranchOffice.setCompany(racsRepository.findByName(bo.getRACSCompanyName()));
							racsBranchOffice.setLatitude(bo.getLatitude());
							racsBranchOffice.setLongitude(bo.getLongitude());
							racsBranchOffice.setName(bo.getName());
							
							branchOfficesOfRACS.add(racsBranchOffice);
						}
					}
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (yearOfManufacture == c.getYearOfManufacture() && manufacturer.equals(c.getManufacturer())) {
								matchingCars.add(c);
							}
						}

					}
				}
			} else if (!name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACSDTO r : racss) {
					branchOffices = r.getBranchOffices();
					
					
					RACSBranchOffice racsBranchOffice = new RACSBranchOffice();
					for (RACSBranchOfficeDTO bo : branchOffices) {
						if (bo instanceof RACSBranchOfficeDTO) {
							Set<Car> cars = new HashSet<Car>();
							Set<CarDTO> cardtos = bo.getCars();
							for (CarDTO cdto : cardtos) {
								Car c = new Car();
								c.setAverageScore(cdto.getAverageScore());
								c.setColor(cdto.getColor());
								c.setDiscounts(cdto.getDiscounts());
								c.setManufacturer(cdto.getManufacturer());
								c.setName(cdto.getName());
								c.setNumberOfVotes(cdto.getNumberOfVotes());
								c.setPricePerHour(cdto.getPricePerHour());
								c.setRACSBranchOffice(racsBranchOfficeRepository.findByName(cdto.getRacsBranchOfficeName()));
								c.setYearOfManufacture(cdto.getYearOfManufacture());
								cars.add(c);
							}
							racsBranchOffice.setCars(cars);
							racsBranchOffice.setCompany(racsRepository.findByName(bo.getRACSCompanyName()));
							racsBranchOffice.setLatitude(bo.getLatitude());
							racsBranchOffice.setLongitude(bo.getLongitude());
							racsBranchOffice.setName(bo.getName());
							
							branchOfficesOfRACS.add(racsBranchOffice);
						}
					}
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (name.equals(c.getName()) && yearOfManufacture == c.getYearOfManufacture()) {
								matchingCars.add(c);
							}
						}

					}
				}
			} else {
				for (RACSDTO r : racss) {
					branchOffices = r.getBranchOffices();
					
					
					RACSBranchOffice racsBranchOffice = new RACSBranchOffice();
					for (RACSBranchOfficeDTO bo : branchOffices) {
						if (bo instanceof RACSBranchOfficeDTO) {
							Set<Car> cars = new HashSet<Car>();
							Set<CarDTO> cardtos = bo.getCars();
							for (CarDTO cdto : cardtos) {
								Car c = new Car();
								c.setAverageScore(cdto.getAverageScore());
								c.setColor(cdto.getColor());
								c.setDiscounts(cdto.getDiscounts());
								c.setManufacturer(cdto.getManufacturer());
								c.setName(cdto.getName());
								c.setNumberOfVotes(cdto.getNumberOfVotes());
								c.setPricePerHour(cdto.getPricePerHour());
								c.setRACSBranchOffice(racsBranchOfficeRepository.findByName(cdto.getRacsBranchOfficeName()));
								c.setYearOfManufacture(cdto.getYearOfManufacture());
								cars.add(c);
							}
							racsBranchOffice.setCars(cars);
							racsBranchOffice.setCompany(racsRepository.findByName(bo.getRACSCompanyName()));
							racsBranchOffice.setLatitude(bo.getLatitude());
							racsBranchOffice.setLongitude(bo.getLongitude());
							racsBranchOffice.setName(bo.getName());
							
							branchOfficesOfRACS.add(racsBranchOffice);
						}
					}
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (name.equals(c.getName()) && yearOfManufacture == c.getYearOfManufacture() && manufacturer.equals(c.getManufacturer())) {
								matchingCars.add(c);
							}
						}
					}
				}
			}
		} else { // yearOfManufacture == 0
			if (name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACSDTO r : racss) {
					branchOffices = r.getBranchOffices();
					
					
					RACSBranchOffice racsBranchOffice = new RACSBranchOffice();
					for (RACSBranchOfficeDTO bo : branchOffices) {
						if (bo instanceof RACSBranchOfficeDTO) {
							Set<Car> cars = new HashSet<Car>();
							Set<CarDTO> cardtos = bo.getCars();
							for (CarDTO cdto : cardtos) {
								Car c = new Car();
								c.setAverageScore(cdto.getAverageScore());
								c.setColor(cdto.getColor());
								c.setDiscounts(cdto.getDiscounts());
								c.setManufacturer(cdto.getManufacturer());
								c.setName(cdto.getName());
								c.setNumberOfVotes(cdto.getNumberOfVotes());
								c.setPricePerHour(cdto.getPricePerHour());
								c.setRACSBranchOffice(racsBranchOfficeRepository.findByName(cdto.getRacsBranchOfficeName()));
								c.setYearOfManufacture(cdto.getYearOfManufacture());
								cars.add(c);
							}
							racsBranchOffice.setCars(cars);
							racsBranchOffice.setCompany(racsRepository.findByName(bo.getRACSCompanyName()));
							racsBranchOffice.setLatitude(bo.getLatitude());
							racsBranchOffice.setLongitude(bo.getLongitude());
							racsBranchOffice.setName(bo.getName());
							
							branchOfficesOfRACS.add(racsBranchOffice);
						}
					}
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							matchingCars.add(c);
						}
					}
				}
			} else if (name.trim().isEmpty() && !manufacturer.trim().isEmpty()) {
				for (RACSDTO r : racss) {
					branchOffices = r.getBranchOffices();
					
					
					RACSBranchOffice racsBranchOffice = new RACSBranchOffice();
					for (RACSBranchOfficeDTO bo : branchOffices) {
						if (bo instanceof RACSBranchOfficeDTO) {
							Set<Car> cars = new HashSet<Car>();
							Set<CarDTO> cardtos = bo.getCars();
							for (CarDTO cdto : cardtos) {
								Car c = new Car();
								c.setAverageScore(cdto.getAverageScore());
								c.setColor(cdto.getColor());
								c.setDiscounts(cdto.getDiscounts());
								c.setManufacturer(cdto.getManufacturer());
								c.setName(cdto.getName());
								c.setNumberOfVotes(cdto.getNumberOfVotes());
								c.setPricePerHour(cdto.getPricePerHour());
								c.setRACSBranchOffice(racsBranchOfficeRepository.findByName(cdto.getRacsBranchOfficeName()));
								c.setYearOfManufacture(cdto.getYearOfManufacture());
								cars.add(c);
							}
							racsBranchOffice.setCars(cars);
							racsBranchOffice.setCompany(racsRepository.findByName(bo.getRACSCompanyName()));
							racsBranchOffice.setLatitude(bo.getLatitude());
							racsBranchOffice.setLongitude(bo.getLongitude());
							racsBranchOffice.setName(bo.getName());
							
							branchOfficesOfRACS.add(racsBranchOffice);
						}
					}
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (manufacturer.equals(c.getManufacturer())) {
								matchingCars.add(c);
							}
						}
					}
				}
			} else if (!name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACSDTO r : racss) {
					branchOffices = r.getBranchOffices();
					
					
					RACSBranchOffice racsBranchOffice = new RACSBranchOffice();
					for (RACSBranchOfficeDTO bo : branchOffices) {
						if (bo instanceof RACSBranchOfficeDTO) {
							Set<Car> cars = new HashSet<Car>();
							Set<CarDTO> cardtos = bo.getCars();
							for (CarDTO cdto : cardtos) {
								Car c = new Car();
								c.setAverageScore(cdto.getAverageScore());
								c.setColor(cdto.getColor());
								c.setDiscounts(cdto.getDiscounts());
								c.setManufacturer(cdto.getManufacturer());
								c.setName(cdto.getName());
								c.setNumberOfVotes(cdto.getNumberOfVotes());
								c.setPricePerHour(cdto.getPricePerHour());
								c.setRACSBranchOffice(racsBranchOfficeRepository.findByName(cdto.getRacsBranchOfficeName()));
								c.setYearOfManufacture(cdto.getYearOfManufacture());
								cars.add(c);
							}
							racsBranchOffice.setCars(cars);
							racsBranchOffice.setCompany(racsRepository.findByName(bo.getRACSCompanyName()));
							racsBranchOffice.setLatitude(bo.getLatitude());
							racsBranchOffice.setLongitude(bo.getLongitude());
							racsBranchOffice.setName(bo.getName());
							
							branchOfficesOfRACS.add(racsBranchOffice);
						}
					}
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (name.equals(c.getName())) {
								matchingCars.add(c);
							}
						}
					}
				}
			} else {
				for (RACSDTO r : racss) {
					branchOffices = r.getBranchOffices();
					
					RACSBranchOffice racsBranchOffice = new RACSBranchOffice();
					for (RACSBranchOfficeDTO bo : branchOffices) {
						if (bo instanceof RACSBranchOfficeDTO) {
							Set<Car> cars = new HashSet<Car>();
							Set<CarDTO> cardtos = bo.getCars();
							for (CarDTO cdto : cardtos) {
								Car c = new Car();
								c.setAverageScore(cdto.getAverageScore());
								c.setColor(cdto.getColor());
								c.setDiscounts(cdto.getDiscounts());
								c.setManufacturer(cdto.getManufacturer());
								c.setName(cdto.getName());
								c.setNumberOfVotes(cdto.getNumberOfVotes());
								c.setPricePerHour(cdto.getPricePerHour());
								c.setRACSBranchOffice(racsBranchOfficeRepository.findByName(cdto.getRacsBranchOfficeName()));
								c.setYearOfManufacture(cdto.getYearOfManufacture());
								cars.add(c);
							}
							racsBranchOffice.setCars(cars);
							racsBranchOffice.setCompany(racsRepository.findByName(bo.getRACSCompanyName()));
							racsBranchOffice.setLatitude(bo.getLatitude());
							racsBranchOffice.setLongitude(bo.getLongitude());
							racsBranchOffice.setName(bo.getName());
							
							branchOfficesOfRACS.add(racsBranchOffice);
						}
					}
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (name.equals(c.getName()) && manufacturer.equals(c.getManufacturer())) {
								matchingCars.add(c);
							}
						}
					}
				}
			}
		}
		return matchingCars;

	}

	public HashMap<String, Float> getRevenueForPeriod(String racsName, String startTime, String endTime) {
		HashMap<String, Float> dayRevenue = new HashMap<String, Float>();
		
		Date startDate = parseDate(startTime);
		Date endDate = parseDate(endTime);
		
		if (startDate == null || endDate == null) return null;
		
		DateTime dt = new DateTime(startDate);
		
		String moy = String.valueOf(dt.getMonthOfYear());
		if (moy.length() == 1) moy = "0" + moy;
		Date currentDay = parseDate(dt.getDayOfMonth() + "-" + moy + "-" + dt.getYear() + " 00:00");
		
		float revenueForCurrentDay;
		Collection<CarReservation> carReservations = getCarReservationsOfRacs(racsName);
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
	
	public Collection<CarReservation> getCarReservationsOfRacs(String racsName) {
		RACS r = racsRepository.findByName(racsName);
		Set<BranchOffice> rbos = r.getBranchOffices();
		Set<CarReservation> crs = new HashSet<CarReservation>();
		for (BranchOffice bo : rbos) {
			crs.addAll(carReservationRepository.findCarReservationsOfRacsBranchOffice(bo.getId()));
		}
		return crs;
	}
	
	// <String, Integer> = <date of reservation, number of reservations for date>
	public HashMap<String, Integer> getNumberOfCarReservationsOfRacs(String racsName, String startTime, String endTime, String unit) {
		HashMap<String, Integer> dateNumberOfReservations = new HashMap<String, Integer>();
		Collection<CarReservation> carReservations = getCarReservationsOfRacs(racsName);
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
		RACS racs = racsRepository.findById(racsId).get();
		RACSAdmin racsAdmin = racsAdminRepository.findByEmail(email);
		if (racs != null && racsAdmin != null) {
			racsAdmin.setRACS(racs);
			
			racs.setAdmin(racsAdmin);
			racsRepository.save(racs);
			return new RACSAdminDTO(racsAdmin);
				
		}
		
		return null;
	}

	// <String, Float> - <car id: car manufacturer car name, average rating>
	public HashMap<String, Float> getAverageRatingForEachCarOfRacs(RACSDTO racsDTO) {
		
		Set<RACSBranchOfficeDTO> rbos = racsDTO.getBranchOffices();
		Set<Car> cars = new HashSet<Car>();
		Set<Car> carsOfRBO = new HashSet<Car>();
		RACSBranchOffice rbo = new RACSBranchOffice();
		for (RACSBranchOfficeDTO bo : rbos) {
			rbo = racsBranchOfficeRepository.findByName(bo.getName());
			carsOfRBO = rbo.getCars();
			for (Car c : carsOfRBO) {
				cars.add(c);
			}
		}
		
		HashMap<String, Float> carNameAverageRating = new HashMap<String, Float>();
		for (Car c : cars) {
			carNameAverageRating.put("#" + c.getId() + ": " + c.getManufacturer() + " " + c.getName(), c.getAverageScore());
		}
		return carNameAverageRating;
	}


}
