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
import com.FlightsReservations.domain.dto.FlightRatingDTO;
import com.FlightsReservations.domain.dto.FlightSearchQueryDTO;
import com.FlightsReservations.domain.dto.FlightSearchRequestDTO;
import com.FlightsReservations.domain.enums.SeatType;
import com.FlightsReservations.domain.enums.TripType;
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
			createSeats(f, dto);
			f = repository.save(f);
			return new FlightDTO(f);
		}
		return null;
	}
	
	
	public List<List<FlightDTO>> search(FlightSearchRequestDTO dto) {
		if (verifySearchDTO(dto)) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			
			List<List<FlightDTO>> toCombine = new ArrayList<>();			
			Pageable pageable = PageRequest.of(dto.getPageNumber(), dto.getResultCount());
			Long seatOrdinal = getSeatOrdinal(dto.getSeatType());
			
			for (FlightSearchQueryDTO query : dto.getQueries()) {		
				Page<Flight> flights = repository.search(
						sdf.format(query.getTakeoffDate()), 
						query.getStartCity(), 
						query.getEndCity(), 
						dto.getNumOfPassengers(),
						seatOrdinal,
						pageable);
				
				List<FlightDTO> dtos = new ArrayList<>();
				for (Flight f : flights.getContent()) {
					dtos.add(new FlightDTO(f));
				}
				toCombine.add(dtos);
			}
			List<List<FlightDTO>> results = combineAll(toCombine);
			results = filterBadDates(results);
			return results;
		}
		return null;
	}

	// remove flight results where landing time of first flight is after takeoff time of return flight
	private List<List<FlightDTO>> filterBadDates(List<List<FlightDTO>> results) {
		List<List<FlightDTO>> filtered = new ArrayList<>();
		
		Date landingTime;
		boolean flag;
		for (List<FlightDTO> result : results) {
			landingTime = result.get(0).getLandingTime();
			flag = false;
			for (int i = 1; i < result.size(); i++) {
				if (!landingTime.before(result.get(i).getTakeoffTime())) {
					flag = true;
					break;
				} else {
					landingTime = result.get(i).getLandingTime();
				}
			}
			if (!flag)
				filtered.add(result);
		}
		
		return filtered;
	}
	
	
	private List<List<FlightDTO>> combineAll(List<List<FlightDTO>> toCombine) {
		List<List<FlightDTO>> result = new ArrayList<>();
		for (List<FlightDTO> list : toCombine)
			result = combineTwo(result, list);
		return result;
		
	} 
	
	private List<List<FlightDTO>> combineTwo(List<List<FlightDTO>> result, List<FlightDTO> list) {
		List<List<FlightDTO>> newResult = new ArrayList<>();
		
		if (result.isEmpty()) {
			for (FlightDTO s : list) {
				List<FlightDTO> temp = new ArrayList<>();
				temp.add(s);
				newResult.add(temp);
			}
		} else {
			for (List<FlightDTO> r : result) {
				for (FlightDTO s : list) {
					List<FlightDTO> temp = new ArrayList<>();
					temp.addAll(r);
					temp.add(s);
					newResult.add(temp);
				}
			}
		}
		
		return newResult;
	}
	
	
	
	private Long getSeatOrdinal(SeatType seatType) {
		if (seatType != null)
			return (long) seatType.ordinal();
		return null;
	}
	
	
	private void createSeats(Flight f, CreateFlightDTO dto) {
		Seat s;
		int offset = 0;
		int first = dto.getFirstClassNum();
		int business = dto.getBusinessClassNum();
		int eco = dto.getEconomicClassNum();
		
		for (int i = offset; i < first; i++) {
			s = new Seat(i+1, true, SeatType.FIRST, f);
			f.getSeats().add(s);
		}
		
		offset = first;	
		for (int i = offset; i < first + business; i++) {
			s = new Seat(i+1, true, SeatType.BUSINESS, f);
			f.getSeats().add(s);
		}
		
		offset += business;
		for (int i = offset; i < first + business + eco; i++) {
			s = new Seat(i+1, true, SeatType.ECONOMY, f);
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
		
		return true;
	}
	
	public Flight findOne(Long id) {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public Set<FlightDTO> findAll() {
		List<Flight> flights = repository.findAll();
		Set<FlightDTO> dtos = new HashSet<>();
		for (Flight f : flights) 
			dtos.add(new FlightDTO(f));
		return dtos;
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
