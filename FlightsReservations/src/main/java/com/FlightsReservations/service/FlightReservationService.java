package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.domain.AirlineAdmin;
import com.FlightsReservations.domain.AirlinePriceList;
import com.FlightsReservations.domain.Flight;
import com.FlightsReservations.domain.FlightInvite;
import com.FlightsReservations.domain.FlightReservation;
import com.FlightsReservations.domain.Passenger;
import com.FlightsReservations.domain.Seat;
import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.FlightInviteDTO;
import com.FlightsReservations.domain.dto.FlightReservationDTO;
import com.FlightsReservations.domain.dto.FlightReservationDetailsDTO;
import com.FlightsReservations.domain.dto.FlightsReservationRequestDTO;
import com.FlightsReservations.domain.dto.PassengerDTO;
import com.FlightsReservations.domain.dto.QuickFlightReservationDTO;
import com.FlightsReservations.domain.enums.SeatType;
import com.FlightsReservations.repository.FlightInviteRepository;
import com.FlightsReservations.repository.FlightRepository;
import com.FlightsReservations.repository.FlightReservationRepository;
import com.FlightsReservations.repository.UserRepository;

@Service
public class FlightReservationService {

	@Autowired
	private FlightReservationRepository repository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FlightRepository flightRepository;
	
	@Autowired
	private FlightInviteRepository flightInviteRepository;
	
	@Autowired
	private EmailService emailService;

	public FlightReservationDTO create(FlightsReservationRequestDTO reservationDTO) {
			
		if (verifyCreateReservation(reservationDTO)) {
			Airline a = ((AirlineAdmin) SecurityContextHolder.getContext().getAuthentication()).getAirline();
			User owner = userRepository.findByEmail(reservationDTO.getOwnerEmail());
			FlightReservation r = new FlightReservation(a,new Date(), (float) 0, owner, true);
			a.getReservations().add(r);
			
			for (FlightReservationDetailsDTO detailDTO : reservationDTO.getFlights()) {
				Flight f = flightRepository.findById(detailDTO.getFlightId()).get();
				r.getFlights().add(f);
				f.getReservations().add(r);
				
				for (PassengerDTO passengerDTO : detailDTO.getPassengers()) {
					Seat s = findSeat(f, passengerDTO.getSeatNumber());
					s.setAvailable(false);
					r.setPrice(r.getPrice() + calculatePrice(s, f));
					Passenger p= new Passenger(passengerDTO, s);
					r.getPassengers().add(p);
				}
				
				for (FlightInviteDTO inviteDTO : detailDTO.getInvites()) {
					Seat s = findSeat(f, inviteDTO.getSeatNumber());
					s.setAvailable(false);
					FlightInvite i = new FlightInvite(r, inviteDTO.getFriendEmail(), s.getId());
					r.setPrice(r.getPrice() + calculatePrice(s, f));
					r.getInvites().add(i);
					sendInviteEmail(owner, i);
				}
			}
			repository.save(r);
			return new FlightReservationDTO(r);
		}
		return null;
	}
	
	// TODO: ADD ADDITIONAL DISCOUNT BASED ON USER POINTS
	private float calculatePrice(Seat s, Flight f) {
		SeatType t = s.getType(); 
		AirlinePriceList pricelist = f.getAirline().getPricelist();
		if (t.equals(SeatType.FIRST)) {
			return pricelist.getFirst() + f.getPrice();
		}
		else if (t.equals(SeatType.BUSINESS)) {
			return pricelist.getBussines()+ f.getPrice();
		}
		else {
			return pricelist.getEconomic() + f.getPrice();
		}
	}

	
	private void sendInviteEmail(User owner, FlightInvite invite) {
		String subject = "New trip invite!";
		String template = "Your friend %s invited you to a trip.\nYou can view details and accept invite on this page: http://localhost:8080/flightsFront/user/flightInvitation.html/%s";
		String text = String.format(template, owner.getFirstName() + " " + owner.getLastName(), invite.getId());
		emailService.sendEmail("ivkovicdjordje1997@gmail.com", subject, text);
	}

	
	public boolean acceptInvite(Long inviteId, String invitedEmail) {
		if (verifyAcceptInvite(inviteId, invitedEmail)) {
			User invitedUser = userRepository.findByEmail(invitedEmail);
			FlightInvite invite = flightInviteRepository.findById(inviteId).get();
			invite.setAccepted(true);
			
			FlightReservation r = new FlightReservation();
			r.setOwner(invitedUser);
			invitedUser.getFlightReservations().add(r);
			r.setPrice(invite.getReservation().getPrice());
			r.setDateOfReservation(new Date());
			r.setDiscount((float)0);
			r.setConfirmed(true);
			
			for (Flight f : invite.getReservation().getFlights()) {
				r.getFlights().add(f);
				f.getReservations().add(r);
				//Passenger p = new Passenger(
					//	invitedUser.getFirstName(), 
						//invitedUser.getLastName(), 
					//	invite.getPassport(),
					//	seatRepository.findById(invite.getSeatId()).get());
				//r.getPassengers().add(p);
			}
			repository.save(r);
			return true;
		}
		return false;
	}

	
	private boolean verifyAcceptInvite(Long inviteId, String invitedEmail) {
		Optional<FlightInvite> opt = flightInviteRepository.findById(inviteId);
		if (!opt.isPresent())
			return false;
		
		FlightInvite invite = opt.get();
		
		if (invite.isAccepted())
			return false;
		
		if (userRepository.findByEmail(invitedEmail) == null)
			return false;
		
		if (!invite.getFriendEmail().equals(invitedEmail))
			return false;
		
		if (new Date().after(invite.getExpirationDate()))
			return false;
			
		return true;
	}
	
	
	private boolean verifyCreateReservation(FlightsReservationRequestDTO dto) {
		User u = userRepository.findByEmail(dto.getOwnerEmail());
		if (u == null)
			return false;
		
		for (FlightReservationDetailsDTO detailDTO : dto.getFlights()) {
			Optional<Flight> opt = flightRepository.findById(detailDTO.getFlightId());
			if (opt.isPresent()) {
				if (!verifyPassportUniqueness(opt.get(), detailDTO.getPassengers()))
					return false;
				if (!verifyInvites(u, detailDTO.getInvites()))
					return false;
				if (!verifyRequestedSeats(opt.get(), detailDTO.getPassengers(), detailDTO.getInvites()))
					return false;
			} 
			else 
				return false;
		}
		return true;
	}
	
	
	private boolean verifyPassportUniqueness(Flight flight, List<PassengerDTO> passengers) {
		Set<String> flightPassports = flightRepository.findPassportsInFlight(flight.getId());
		for (PassengerDTO passenger : passengers) {
			if (flightPassports.contains(passenger.getPassport()))
				return false;
			flightPassports.add(passenger.getPassport());
		}
		return true;
	}
	
	
	private boolean verifyInvites(User sender, List<FlightInviteDTO> invites) {
		for (FlightInviteDTO invite : invites) {
			User reciever = userRepository.findByEmail(invite.getFriendEmail());
			if (reciever == null)
				return false;
			if (userRepository.areFriends(sender.getId(), reciever.getId()) == 0)
				return false;
		}
		return true;
	}
	
	
	private boolean verifyRequestedSeats(Flight flight, List<PassengerDTO> passengers, List<FlightInviteDTO> invites) {
		if (!verifySeatUniqueness(passengers, invites))
			return false;
		
		if (!verifySeatAvailability(flight, passengers, invites)) 
			return false;
		
		return true;
	}
	
	
	private boolean verifySeatAvailability(Flight flight, List<PassengerDTO> passengers,
			List<FlightInviteDTO> invites) {
		Set<Integer> seatNumbers = new HashSet<>();
		for (PassengerDTO p : passengers) seatNumbers.add(p.getSeatNumber());
		for (FlightInviteDTO i : invites) seatNumbers.add(i.getSeatNumber());
		
		for (Integer seatNum : seatNumbers) {
			Seat s = findSeat(flight, seatNum);
			if (s == null)
				return false;
			if (!s.isAvailable())
				return false;
		}
		return true;
	}

	
	private boolean verifySeatUniqueness(List<PassengerDTO> passengers, List<FlightInviteDTO> invites) {
		int numberOfRequestedSeats = passengers.size() + invites.size();
		Set<Integer> seatNumbers = new HashSet<>();
		for (PassengerDTO p : passengers) seatNumbers.add(p.getSeatNumber());
		for (FlightInviteDTO i : invites) seatNumbers.add(i.getSeatNumber());
		if (seatNumbers.size() != numberOfRequestedSeats)
			return false;
		return true;
	}


	private Seat findSeat(Flight f, Integer seatNum) {
		for (Seat s : f.getSeats())
			if (s.getSeatNumber().equals(seatNum))
				return s;
		return null;
	}

	
	public boolean cancel(Long id) {
		FlightReservation fr = repository.findById(id).get();
		
		if (fr != null) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date minDate = new Date();
			try {
				minDate = sdf.parse("2050-05-05");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			for (Flight f : fr.getFlights()) {
				Date takeoffTime = f.getTakeoffTime();
				if (takeoffTime.before(minDate)) {
					minDate = takeoffTime;
				}
			}
			// difference between now (cancellation time) and reservation start time in hours
			long diff = (minDate.getTime() - now.getTime()) / (60 * 60 * 1000);
			System.out.println(diff);
			// reservation cannot be cancelled less than two days before the reservation starts
			if (diff < 3) return false;
			
			fr.setConfirmed(false);
			repository.save(fr);
			return true;
		}
		return false;
	}

	
	public boolean createQuickReservation(Long id, Integer seatNum, Float discount) {
		if (verifyCreateQR(id, seatNum)) {
			Airline a = ((AirlineAdmin) SecurityContextHolder.getContext().getAuthentication()).getAirline();
			Flight f = flightRepository.findById(id).get();
			FlightReservation r = new FlightReservation(a, new Date(), (float)0, null, true);
			a.getReservations().add(r);
			r.setDiscount(discount);
			r.getFlights().add(f);
			f.getReservations().add(r);
			
			Seat s = findSeat(f, seatNum);
			s.setAvailable(false);
			Passenger p = new Passenger();
			p.setSeat(s);
			r.getPassengers().add(p);
			r.setPrice(calculatePrice(s, f));
			repository.save(r);
			return true;
		}
		return false;
	}
	
	
	public FlightReservationDTO takeQR(Long reservationId, String ownerEmail, String passport) {
		if (verifyTakeQR(reservationId, ownerEmail, passport)) {
			User owner = userRepository.findByEmail(ownerEmail);
			FlightReservation r = repository.findById(reservationId).get();
			r.setOwner(owner);
			owner.getFlightReservations().add(r);
			Passenger p = (Passenger) r.getPassengers().toArray()[0];
			p.setName(owner.getFirstName());
			p.setSurname(owner.getLastName());
			p.setPassport(passport);
			repository.save(r);
			return new FlightReservationDTO(r);
		}
		return null;
	}
	
	
	private boolean verifyCreateQR(Long id, Integer seatNum) {
		Optional<Flight> opt = flightRepository.findById(id);
		if (!opt.isPresent())
			return false;
		
		Seat s = findSeat(opt.get(), seatNum);
		if (s == null)
			return false;
		
		if (!s.isAvailable())
			return false;
		
		return true;
	}
	
	
	private boolean verifyTakeQR(Long reservationId, String ownerEmail, String passport) {
		Optional<FlightReservation> opt = repository.findById(reservationId);
		if (!opt.isPresent())
			return false;
		
		FlightReservation r = opt.get();
		if (r.getDiscount() == 0)
			return false;
		
		if (r.getPassengers().size() != 1)
			return false;
		
		Passenger p = (Passenger) r.getPassengers().toArray()[0];
		if (p.getSeat().isAvailable())
			return false;
	
		User u = userRepository.findByEmail(ownerEmail);
		if (u == null)
			return false;	
		
		List<PassengerDTO> passengers = new ArrayList<>();
		PassengerDTO ps = new PassengerDTO((Passenger)r.getPassengers().toArray()[0]);
		ps.setPassport(passport);
		passengers.add(ps);
		
		Flight f = (Flight) r.getFlights().toArray()[0];
		if (!verifyPassportUniqueness(f, passengers))
			return false;
		
		return true;		
	}
	
	public List<QuickFlightReservationDTO> getQuickReservations(String airlineName) {
		List<QuickFlightReservationDTO> results = new ArrayList<>();
		List<FlightReservation> quickReservations = repository.findQuickReservations();
		for (FlightReservation fr : quickReservations) {
			Flight f = (Flight) fr.getFlights().toArray()[0];
			if (f.getAirline().getName().contentEquals(airlineName))
				results.add(new QuickFlightReservationDTO(fr));
		}
		return results;
	}
	
}
