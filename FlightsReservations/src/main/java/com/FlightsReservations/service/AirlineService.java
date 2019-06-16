package com.FlightsReservations.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.FlightsReservations.common.DateIterator;
import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.domain.AirlineAdmin;
import com.FlightsReservations.domain.AirlinePriceList;
import com.FlightsReservations.domain.Airport;
import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.FlightReservation;
import com.FlightsReservations.domain.dto.AirlineDTO;
import com.FlightsReservations.domain.dto.AirportDTO;
import com.FlightsReservations.domain.dto.FlightDTO;
import com.FlightsReservations.domain.dto.PricelistDTO;
import com.FlightsReservations.repository.AirlineRepository;
import com.FlightsReservations.repository.AirportRepository;

@Service
public class AirlineService {

	@Autowired
	private AirlineRepository repository;
	
	@Autowired
	private AirportRepository airportRepository;

	public AirlineDTO create(AirlineDTO t) {
		Airline a = repository.findByName(t.getName());
		if (a == null) {
			a = new Airline(
					t.getName(), 
					t.getLongitude(), 
					t.getLatitude(),
					t.getCity(),
					t.getState(),
					t.getPromoDescription(), 
					t.getAverageScore(), t.getNumberOfVotes());
			AirlinePriceList pl = new AirlinePriceList(a, 0, 0, 0);
			a.setPricelist(pl);
			repository.save(a);
			return createDTO(a);
		}
		return null;
	}

	public boolean update(String airline, String promo) {
		Airline a = repository.findByName(airline);
		if (a != null) {
			a.setPromoDescription(promo);
			repository.save(a);
			return true;
		}
		return false;
	}

	
	public AirlineDTO findOne(String name) {
		Airline a = repository.findByName(name);
		if (a != null)
			return createDTO(a);
		return null;
	}
	
	
	public List<AirlineDTO> findAll() {
		List<AirlineDTO> dtos = new ArrayList<>();
		for (Airline a : repository.findAll()) 
			dtos.add(createDTO(a));
		return dtos;
	}	
	
	
	public AirportDTO addAirport(String airlineName, String airportName) {
		Airline airline = repository.findByName(airlineName);
		Airport airport = airportRepository.findByName(airportName);
			
		if (airline != null && airport != null) {
			for (Airport a : airline.getAirports())
				if (a.getName().equals(airport.getName()))
					return null;
			
			airline.getAirports().add(airport);
			repository.save(airline);
			return new AirportDTO(airport);
		}
		return null;
	}
	
	
	public List<AirportDTO> getAirports(String airlineName) {
		Airline airline = repository.findByName(airlineName);
		List<AirportDTO> dtos = new ArrayList<AirportDTO>();
		if (airline != null) {
			for (Airport a : airline.getAirports())
				dtos.add(new AirportDTO(a));
		}
		return dtos;
	}
	
	
	public PricelistDTO getPricelist(String airline) {
		Airline a = repository.findByName(airline);
		if (a != null && a.getPricelist() != null)
			return new PricelistDTO(a.getPricelist());
		return null;
	}
	
	
	
	public boolean setPricelist(PricelistDTO dto) {
		Airline a = repository.findByName(dto.getAirline());
		if (a != null) {
			AirlinePriceList p = new AirlinePriceList(a, dto.getBusiness(), dto.getEconomic(), dto.getFirst());
			a.setPricelist(p);
			repository.save(a);
			return true;
		}
		return false;
	}
	
	
	public List<FlightDTO> getFlights(String airline) {
		Airline a = repository.findByName(airline);
		if (a != null) {
			List<FlightDTO> dtos = new ArrayList<>();
			for (Flight f : a.getFlights())
				dtos.add(new FlightDTO(f));
			return dtos;
		}
		return new ArrayList<>();
	}
	
	public Map<String,Integer> getCountReport(String pattern) {
		// daily reports:   dd-MM-yyyy
		// monthly reports: MM-yyyy
		// weekly reports:  W-MM-yyyy (W - week of month)
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		AirlineAdmin admin = (AirlineAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Airline a = repository.findByName(admin.getAirline().getName());
		
		Set<FlightReservation> reservations = a.getReservations();
		reservations.size();
		
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		LinkedHashMap<String, Integer> ret = new LinkedHashMap<>();		
		DateIterator it = new DateIterator(new Date(), -45);
		Date cntDate = null;
		while ((cntDate = it.getCounter()) != null) {
			String cntStr = sdf.format(cntDate.getTime());
			if (!ret.containsKey(cntStr))
				ret.put(cntStr, 0);
			it.increment();
		}
		
		for (FlightReservation r : reservations) {
			String rStr = sdf.format(r.getDateOfReservation());
			if (ret.containsKey(rStr)) 
				ret.put(rStr, ret.get(rStr) + 1);
		}
		
		return ret;
	}
	
	
	public Map<String, Float> getIncomeForPeriod(String startDateStr, String endDateStr) {
		System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());

		LinkedHashMap<String, Float> income = new LinkedHashMap<>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		
		Date startDate;
		Date endDate;
		try {
			startDate = sdf.parse(startDateStr);
			endDate = sdf.parse(endDateStr);
		} catch (Exception ex) {
			return income;
		}
		
		if (startDate.before(endDate)) {
			AirlineAdmin admin = (AirlineAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			Airline a = repository.findByName(admin.getAirline().getName());
			
			Set<FlightReservation> reservations = a.getReservations();
			reservations.size();
			
			DateIterator it = new DateIterator(startDate, endDate);
			Date cntDate = null;
			while ((cntDate = it.getCounter()) != null) {
				String cntStr = sdf.format(cntDate.getTime());
				income.put(cntStr, (float) 0);
				it.increment();
			}
			
			for (FlightReservation r : reservations) {
				String rStr = sdf.format(r.getDateOfReservation());
				if (income.containsKey(rStr)) {
					income.put(rStr, income.get(rStr) + r.getPrice());
				}
			}
		}
		
		return income;
	}
	
	private AirlineDTO createDTO(Airline airline) {
		AirlineDTO dto = new AirlineDTO(airline);
		
		for (Airport a : airline.getAirports()) 
			dto.getAirports().add(a.getName());
		
		for (Flight f : airline.getFlights())
			dto.getFlights().add(new FlightDTO(f));
		
		return dto;
	}
}
