package com.FlightsReservations.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.Discount;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.RACSBranchOffice;
import com.FlightsReservations.domain.dto.CarDTO;
import com.FlightsReservations.domain.dto.RACSBranchOfficeDTO;
import com.FlightsReservations.repository.RACSBranchOfficeRepository;
import com.FlightsReservations.repository.RACSRepository;

@Component
public class RACSBranchOfficeService {
	@Autowired
	RACSBranchOfficeRepository racsBranchOfficeRepository;
	
	@Autowired
	RACSRepository racsRepository;
	
	public RACSBranchOffice create(RACSBranchOfficeDTO t) {
		Set<CarDTO> cars = t.getCars();
		if (cars != null) {
			for (CarDTO c : cars) {
				if (c.getDiscounts() == null)
					c.setDiscounts(new HashSet<Discount>());
			}
		}
		RACSBranchOffice rbo = new RACSBranchOffice();
		rbo.setName(t.getName());
		RACS racs = racsRepository.findByName(t.getRACSCompanyName());
		rbo.setRacs(racs);
		Set<Car> carsToBeAdded = new HashSet<Car>();
		Set<CarDTO> cdtos = t.getCars();
		
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
			carsToBeAdded.add(car);
			
		}
		rbo.setCars(carsToBeAdded);
		rbo.setLatitude(t.getLatitude());
		rbo.setLongitude(t.getLongitude());
		
		return	racsBranchOfficeRepository.save(rbo);
	}
	
	public boolean update(RACSBranchOffice t) {
		RACSBranchOffice r = findOne(t.getId());
		if (r != null) {
			r.setLongitude(t.getLongitude());
			r.setLatitude(t.getLatitude());
			r.setCars(t.getCars());
			r.setName(t.getName());
			r.setRacs(t.getRacs());
			racsBranchOfficeRepository.save(r);
			return true;
		}
		return false;
	}
	
	public boolean addCar(CarDTO car) {
		RACSBranchOffice racsBranchOffice = racsBranchOfficeRepository.findByName(car.getRacsBranchOfficeName());
		
		if (racsBranchOffice != null) {
			Car c = new Car(
					car.getManufacturer(), 
					car.getName(), 
					car.getYearOfManufacture(), 
					car.getColor(), 
					racsBranchOffice,
					car.getPricePerHour(),
					car.getAverageScore(),
					car.getNumberOfVotes());

			racsBranchOffice.getCars().add(c);
			racsBranchOfficeRepository.save(racsBranchOffice);
			return true;
		}
		return false;
	}
	
	public RACSBranchOffice findOne(Long id) {
		try {
			return racsBranchOfficeRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public RACSBranchOfficeDTO findByName(String name) {
		try {
			return new RACSBranchOfficeDTO(racsBranchOfficeRepository.findByName(name));
			
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public void delete(Long id) {
		racsBranchOfficeRepository.deleteById(id);
	}

	public Collection<RACSBranchOffice> findAll() {
		return racsBranchOfficeRepository.findAll();
	}

}
