package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.RACSBranchOffice;
import com.FlightsReservations.repository.RACSBranchOfficeRepository;
import com.FlightsReservations.repository.RACSRepository;

@Component
public class RACSService {

	@Autowired
	RACSRepository repository;
	
	@Autowired
	RACSBranchOfficeRepository racsBranchOfficeRepository;

	public RACS create(RACS t) {
		return repository.save(t);
	}

	public boolean update(RACS t) {
		RACS r = findOne(t.getId());
		if (r != null) {
			r.setLongitude(t.getLongitude());
			r.setLatitude(t.getLatitude());
			r.setBranchOffices(t.getBranchOffices());
			r.setPromoDescription(t.getPromoDescription());
			r.setName(t.getName());
			r.setPricelist(t.getPricelist());
			r.setAverageScore(t.getAverageScore());
			r.setNumberOfVotes(t.getNumberOfVotes());
			repository.save(r);
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

	public ArrayList<Car> searchAllCars(Collection<RACS> racss, String name, String manufacturer, int yearOfManufacture) {
		ArrayList<Car> matchingCars = new ArrayList<Car>();
		Set<RACSBranchOffice> branchOfficesOfRACS = new HashSet<RACSBranchOffice>();
		if (yearOfManufacture != 0) {
			if (name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					branchOfficesOfRACS = r.getBranchOffices();
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (yearOfManufacture == c.getYearOfManufacture()) {
								matchingCars.add(c);
							}
						}

					}
				}
			} else if (name.trim().isEmpty() && !manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					branchOfficesOfRACS = r.getBranchOffices();
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (yearOfManufacture == c.getYearOfManufacture() && manufacturer.equals(c.getManufacturer())) {
								matchingCars.add(c);
							}
						}

					}
				}
			} else if (!name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					branchOfficesOfRACS = r.getBranchOffices();
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (name.equals(c.getName()) && yearOfManufacture == c.getYearOfManufacture()) {
								matchingCars.add(c);
							}
						}

					}
				}
			} else {
				for (RACS r : racss) {
					branchOfficesOfRACS = r.getBranchOffices();
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
				for (RACS r : racss) {
					branchOfficesOfRACS = r.getBranchOffices();
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							matchingCars.add(c);
						}
					}
				}
			} else if (name.trim().isEmpty() && !manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					branchOfficesOfRACS = r.getBranchOffices();
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (manufacturer.equals(c.getManufacturer())) {
								matchingCars.add(c);
							}
						}
					}
				}
			} else if (!name.trim().isEmpty() && manufacturer.trim().isEmpty()) {
				for (RACS r : racss) {
					branchOfficesOfRACS = r.getBranchOffices();
					for (RACSBranchOffice rbo : branchOfficesOfRACS) {
						for (Car c : rbo.getCars()) {
							if (name.equals(c.getName())) {
								matchingCars.add(c);
							}
						}
					}
				}
			} else {
				for (RACS r : racss) {
					branchOfficesOfRACS = r.getBranchOffices();
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
}
