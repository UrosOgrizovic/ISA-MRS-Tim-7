package com.FlightsReservations.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.FlightsReservations.common.DistanceCalculator;
import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.domain.Airport;
import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.Seat;
import com.FlightsReservations.domain.dto.CreateFlightDTO;
import com.FlightsReservations.domain.dto.FlightDTO;
import com.FlightsReservations.domain.dto.FlightSearchQueryDTO;
import com.FlightsReservations.domain.dto.FlightSearchRequestDTO;
import com.FlightsReservations.domain.enums.SeatType;
import com.FlightsReservations.domain.enums.TripType;
import com.FlightsReservations.domain.dto.FlightRatingDTO;
import com.FlightsReservations.repository.AirlineRepository;
import com.FlightsReservations.repository.AirportRepository;
import com.FlightsReservations.repository.FlightRepository;

@Service
public class FlightService {

	@Autowired
	private FlightRepository repository;

	@Autowired
	private AirlineRepository airlineRepository;

	@Autowired
	private AirportRepository airportRepository;

	public FlightDTO add(CreateFlightDTO dto) {
		Airline airline = airlineRepository.findByName(dto.getAirlineName());
		Airport start = airportRepository.findByName(dto.getStartAirportName());
		Airport end = airportRepository.findByName(dto.getEndAirportName());

		if (verifyCreateDTO(airline, start, end, dto)) {
			Set<Airport> stops = airportRepository.findByNameIn(dto.getStopNames());
			Long flightTime = (dto.getLandingTime().getTime() - dto.getTakeOffTime().getTime()) / (1000 * 60);
			
			double dist = DistanceCalculator.distance(
					start.getLatitude(), 
					start.getLongitude(), 
					end.getLatitude(), 
					end.getLongitude(), "K");
			
			Flight f = new Flight(
					dto.getTakeOffTime(), 
					dto.getLandingTime(), 
					flightTime.intValue(), 
					dist,
					dto.getPrice(),
					airline, start, end, stops,
					dto.getAverageScore(), dto.getNumberOfVotes());
			
			createSeats(f, dto.getNumberOfSeats(), dto.getFirstClassNum(), dto.getBusinessClassNum());
			
			f = repository.save(f);
			return new FlightDTO(f);
		}
		return null;
	}
	
	
	
	public List<List<FlightDTO>> search(FlightSearchRequestDTO dto) {
		if (verifySearchDTO(dto)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			// prepare results list, pageable object and get id of seat in enum if present
			List<List<FlightDTO>> results = new ArrayList<>();			
			Pageable pageable = PageRequest.of(dto.getStartIndex(), dto.getNumberOfResults());
			Long seatOrdinal = null;
			if (dto.getSeatType() != null)
				seatOrdinal = (long) dto.getSeatType().ordinal();
			
			for (FlightSearchQueryDTO query : dto.getQueries()) {	
				Page<Flight> flights = repository.search(
						sdf.format(query.getTakeOffTime()), 
						sdf.format(query.getLandingTime()), 
						query.getStartAirportName(), 
						query.getEndAirportName(), 
						dto.getNumOfPassengers(),
						seatOrdinal,
						pageable);
				
				List<FlightDTO> dtos = new ArrayList<>();
				for (Flight f : flights.getContent())
					dtos.add(new FlightDTO(f));
				results.add(dtos);
			}
			return results;
		}
		return null;
	}
	
	public Set<FlightDTO> findAll() {
		List<Flight> flights = repository.findAll();
		
		Set<FlightDTO> dtos = new HashSet<>();
		
		for (Flight f : flights) 
			dtos.add(new FlightDTO(f));
		return dtos;
	}
	
	
	private void createSeats(Flight f, int numOfSeats, int numOfFirst, int numOfBusiness) {
		Seat s;
		SeatType type;
		for (int i = 0; i < numOfSeats; i++) {
			if (i < numOfFirst)
				type = SeatType.FIRST;
			else if (numOfFirst < i && i < numOfBusiness) 
				type = SeatType.BUSINESS;
			else 
				type = SeatType.ECONOMY;
			
			s = new Seat(i, true, type, f);
			f.getSeats().add(s);
		}
	}

	private boolean verifyCreateDTO(Airline airline, Airport start, Airport end, CreateFlightDTO dto) {
		/*
		 * Flight validation: 
		 * 1. check if airline exists 
		 * 2. check if start and end exists
		 * 3. check if start and end are equal
		 * 4. check if airline have start and end
		 * 5. check if stops exists
		 * 6. check if airline have stops
		 * 7. check if start and end not in stops
		 * 8. check if dates are correct (take off < landing ) 
		 * 9. check if first+ business + economic = numOfSeats
		 */
		
		// airline exists
		if (airline == null)
			return false;

		// start,end exists
		if (start == null || end == null)
			return false;

		// start != end
		if (start.getId().equals(end.getId()))
			return false;

		// airline have start
		if (airlineRepository.airlineHaveAirport(airline.getId(), start.getId()) == null)
			return false;

		// airline have end
		if (airlineRepository.airlineHaveAirport(airline.getId(), end.getId()) == null)
			return false;

		// stops exists - if query results size different then stop size some stop is not valid 
		Set<Airport> airports = airportRepository.findByNameIn(dto.getStopNames());
		if (airports.size() != dto.getStopNames().size())
			return false;
		
		// airline have stops
		for (Airport a : airports)
			if (airlineRepository.airlineHaveAirport(airline.getId(), a.getId()) == null)
				return false;
		
		// start not in stops
		if (dto.getStopNames().contains(start.getName()))
			return false;

		// end not in stops
		if (dto.getStopNames().contains(end.getName()))
			return false;
		
		// dates are correct (take off < landing )
		if (!dto.getTakeOffTime().before(dto.getLandingTime()))
			return false;

		// number of seats < first class + business
		if (dto.getNumberOfSeats() != (dto.getFirstClassNum() + dto.getBusinessClassNum() + dto.getEconomicClassNum()))
			return false;

		return true;
	}
	
	private boolean verifySearchDTO(FlightSearchRequestDTO dto) {
		// number of flight queries in one way trip must be 1
		if (dto.getTripType().equals(TripType.OneWay) && dto.getQueries().size() != 1)
			return false;
		
		// number of flight queries in round trip must be 1
		if (dto.getTripType().equals(TripType.RoundTrip) && dto.getQueries().size() != 2)
			return false;
		
		// for each flight departure time must be before landing time
		for (FlightSearchQueryDTO query : dto.getQueries())
			if(!query.getTakeOffTime().before(query.getLandingTime()))
				return false;		

		// in round trip landing time of first flight must be before departure time of second flight
		if (dto.getTripType().equals(TripType.RoundTrip)) {
			Date landing = dto.getQueries().get(0).getLandingTime();
			Date departure = dto.getQueries().get(1).getTakeOffTime();
			
			if (!landing.before(departure))
				return false;
		}
		
		return true;
	}
	
	public Flight findOne(Long id) {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public FlightRatingDTO rate(FlightRatingDTO dto) {
		Flight flight = findOne(dto.getId());
		if (flight != null) {
			float newAvgScore = flight.getAverageScore() * flight.getNumberOfVotes() + dto.getAverageScore();
			int newNumberOfVotes = flight.getNumberOfVotes() + 1;
			flight.setNumberOfVotes(newNumberOfVotes);
			flight.setAverageScore(newAvgScore / newNumberOfVotes);
			repository.save(flight);
			return new FlightRatingDTO(flight.getId(), flight.getAverageScore());
		}
		return null;	
	}
}
