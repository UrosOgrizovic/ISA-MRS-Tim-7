package com.FlightsReservations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.Company;
import com.FlightsReservations.domain.FlightReservation;
import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.RACS;
import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.RoomReservation;
import com.FlightsReservations.domain.dto.RatingDTO;
import com.FlightsReservations.repository.CarRepository;
import com.FlightsReservations.repository.CarReservationRepository;
import com.FlightsReservations.repository.CompanyRepository;
import com.FlightsReservations.repository.FlightRepository;
import com.FlightsReservations.repository.FlightReservationRepository;
import com.FlightsReservations.repository.RoomRepository;
import com.FlightsReservations.repository.RoomReservationRepository;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private FlightReservationRepository flightReservationRepository;
	
	@Autowired
	private RoomReservationRepository roomReservationRepository;
	
	@Autowired
	private CarReservationRepository carReservationRepository;
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	public RatingDTO rate(RatingDTO dto) {
		Company c = companyRepository.findByName(dto.getCompanyName());
		
		if (c != null) {
			if (c instanceof Airline) {
				FlightReservation fr = flightReservationRepository.findById(dto.getReservationId()).get();
				/* //TODO: fix flight reservation rating
				// if flight has already been rated
				if (fr.getFlightRating() > 0) {
					return null;
				}
				Set<Float> flightRatings = fr.getFlightRatings();
				Set<Flight> flights = fr.getFlights();
				float newFlightAvgScore = 0;
				int newFlightNumberOfVotes = 0;
				for (Float rating : flightRatings) {
					for (Flight f : flights) {
						newFlightAvgScore = f.getAverageScore() * f.getNumberOfVotes() + dto.getFlightRoomCarRating();
						newFlightNumberOfVotes = f.getNumberOfVotes() + 1;
						f.setNumberOfVotes(newFlightNumberOfVotes);
						f.setAverageScore(newFlightAvgScore / newFlightNumberOfVotes);
						flightRepository.save(f);
					}
				}
				*/
			} else if (c instanceof Hotel) {
				RoomReservation rr = roomReservationRepository.findById(dto.getReservationId()).get();
				if (rr.getRoomRating() > 0) {
					return null;
				}
				Room room = roomRepository.findById(rr.getRoomId()).get();
				
				float newRoomAvgScore = room.getAverageScore() * room.getNumberOfVotes() + dto.getFlightRoomCarRating();
				int newRoomNumberOfVotes = room.getNumberOfVotes() + 1;
				room.setNumberOfVotes(newRoomNumberOfVotes);
				room.setAverageScore(newRoomAvgScore / newRoomNumberOfVotes);
				roomRepository.save(room);
				rr.setRoomRating(dto.getFlightRoomCarRating());
			} else if (c instanceof RACS) {
				CarReservation cr = carReservationRepository.findById(dto.getReservationId()).get();
				if (cr.getCarRating() > 0) {
					return null;
				}
				Car car = carRepository.findById(cr.getCarId()).get();
				float newCarAvgScore = car.getAverageScore() * car.getNumberOfVotes() + dto.getFlightRoomCarRating();
				int newCarNumberOfVotes = car.getNumberOfVotes() + 1;
				car.setNumberOfVotes(newCarNumberOfVotes);
				car.setAverageScore(newCarAvgScore / newCarNumberOfVotes);
				cr.setCarRating(dto.getFlightRoomCarRating());
			}
			float newAvgScore = c.getAverageScore() * c.getNumberOfVotes() + dto.getCompanyRating();
			int newNumberOfVotes = c.getNumberOfVotes() + 1;
			c.setNumberOfVotes(newNumberOfVotes);
			c.setAverageScore(newAvgScore / newNumberOfVotes);
			companyRepository.save(c);
			return new RatingDTO(c.getName(), c.getAverageScore(), dto.getReservationId());
		}		
		return null;
	}
}
