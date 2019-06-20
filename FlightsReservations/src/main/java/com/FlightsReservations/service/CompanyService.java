package com.FlightsReservations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.Company;
import com.FlightsReservations.domain.FlightReservation;
import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.RACSBranchOffice;
import com.FlightsReservations.domain.Rating;
import com.FlightsReservations.domain.Reservation;
import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.RoomReservation;
import com.FlightsReservations.domain.dto.RatingDTO;
import com.FlightsReservations.repository.CarRepository;
import com.FlightsReservations.repository.CarReservationRepository;
import com.FlightsReservations.repository.CompanyRepository;
import com.FlightsReservations.repository.FlightRepository;
import com.FlightsReservations.repository.FlightReservationRepository;
import com.FlightsReservations.repository.RACSBranchOfficeRepository;
import com.FlightsReservations.repository.RatingRepository;
import com.FlightsReservations.repository.ReservationRepository;
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
	private RACSBranchOfficeRepository racsBranchOfficeRepository;
	
	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private RatingRepository ratingRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	public RatingDTO rate(RatingDTO dto) {
		Company company = new Company();
		RACSBranchOffice bo = new RACSBranchOffice();
		if (dto.getCompanyId() != null && dto.getCompanyId() != 0) {
			company = companyRepository.findById(dto.getCompanyId()).get();
		} else {
			bo = racsBranchOfficeRepository.findById(dto.getRacsBranchOfficeId()).get();
			company = bo.getRacs();
		}
		
		
		Rating rating = ratingRepository.findByReservationId(dto.getReservationId());
		Reservation reservation = reservationRepository.findById(dto.getReservationId()).get();
		if (reservation.getRating().getCompanyRating() > 0) {
			// company can't be rated multiple times for one reservation
		} else {
			if (dto.getCompanyRating() > 0) {
				float newAvgScore = company.getAverageScore() * company.getNumberOfVotes() + dto.getCompanyRating();
				int newNumberOfVotes = company.getNumberOfVotes() + 1;
				company.setNumberOfVotes(newNumberOfVotes);
				company.setAverageScore(newAvgScore / newNumberOfVotes);
				companyRepository.save(company);
				reservation.getRating().setCompanyRating(dto.getCompanyRating());
			}
		}
		
		if (reservation.getRating().getFlightRoomCarRating() > 0) {
			// flight/room/car can't be rated multiple times for one reservation
		} else {
			if (dto.getFlightRoomCarRating() > 0) {
				if (company instanceof Airline) {
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
					reservation.getRating().setFlightRoomCarRating(dto.getFlightRoomCarRating());
					reservation.getRating().setCompanyId(dto.getCompanyId());
					*/
					return null;
					
				} else if (company instanceof Hotel) {
					RoomReservation rr = roomReservationRepository.findById(dto.getReservationId()).get();
					if (rr.getRating().getFlightRoomCarRating() > 0) {
						return null;
					}
					Room room = roomRepository.findById(rr.getRoomId()).get();
					
					float newRoomAvgScore = room.getAverageScore() * room.getNumberOfVotes() + dto.getFlightRoomCarRating();
					int newRoomNumberOfVotes = room.getNumberOfVotes() + 1;
					room.setNumberOfVotes(newRoomNumberOfVotes);
					room.setAverageScore(newRoomAvgScore / newRoomNumberOfVotes);
					
					if (rating == null) {
						rating = new Rating(rr, dto.getFlightRoomCarRating(), dto.getCompanyRating());
						rating.setCompanyId(dto.getCompanyId());
						rr.setRating(rating);
					} else {
						rating.setFlightRoomCarRating(dto.getFlightRoomCarRating());
						rating.setCompanyRating(reservation.getRating().getCompanyRating());
					}
					reservation.getRating().setCompanyId(dto.getCompanyId());
					roomRepository.save(room);
					ratingRepository.save(rating);
					reservation.getRating().setFlightRoomCarRating(dto.getFlightRoomCarRating());
				} else if (bo instanceof RACSBranchOffice) {
					CarReservation cr = carReservationRepository.findById(dto.getReservationId()).get();
					if (cr.getRating().getFlightRoomCarRating() > 0) {
						return null;
					}
					
					Car car = carRepository.findById(cr.getCarId()).get();
					float newCarAvgScore = car.getAverageScore() * car.getNumberOfVotes() + dto.getFlightRoomCarRating();
					int newCarNumberOfVotes = car.getNumberOfVotes() + 1;
					car.setNumberOfVotes(newCarNumberOfVotes);
					car.setAverageScore(newCarAvgScore / newCarNumberOfVotes);
									
					if (rating == null) {
						rating = new Rating(cr, dto.getFlightRoomCarRating(), dto.getCompanyRating());
						rating.setRacsBranchOfficeId(dto.getRacsBranchOfficeId());
						cr.setRating(rating);
					} else {
						rating.setFlightRoomCarRating(dto.getFlightRoomCarRating());
						rating.setCompanyRating(reservation.getRating().getCompanyRating());
					}
					
					carRepository.save(car);
					ratingRepository.save(rating);
					reservation.getRating().setFlightRoomCarRating(dto.getFlightRoomCarRating());
				}
			}
			
		}
		
		reservationRepository.save(reservation);
		return dto;
	}
}
