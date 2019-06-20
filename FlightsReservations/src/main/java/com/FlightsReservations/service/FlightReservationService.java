package com.FlightsReservations.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.FlightsReservations.domain.AbstractUser;
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
import com.FlightsReservations.domain.dto.FlightInviteDetailsDTO;
import com.FlightsReservations.domain.dto.FlightReservationDTO;
import com.FlightsReservations.domain.dto.FlightReservationDetailsDTO;
import com.FlightsReservations.domain.dto.FlightsReservationRequestDTO;
import com.FlightsReservations.domain.dto.PassengerDTO;
import com.FlightsReservations.domain.dto.QuickFlightReservationDTO;
import com.FlightsReservations.domain.enums.SeatType;
import com.FlightsReservations.repository.AirlineRepository;
import com.FlightsReservations.repository.FlightInviteRepository;
import com.FlightsReservations.repository.FlightRepository;
import com.FlightsReservations.repository.FlightReservationRepository;
import com.FlightsReservations.repository.SeatRepository;
import com.FlightsReservations.repository.UserRepository;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED)
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
	
	@Autowired
	private AirlineRepository airlineRepository;
	
	@Autowired
	private SeatRepository seatRepository;
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public FlightReservationDTO create(FlightsReservationRequestDTO reservationDTO) {
		
		if (verifyCreateReservation(reservationDTO)) {
			User owner = userRepository.findByEmail(reservationDTO.getOwnerEmail());
			FlightReservation r = new FlightReservation(new Date(), (float) 0, owner, true);
			HashMap<String, ArrayList<Long>> invites = new HashMap<String, ArrayList<Long>>();
			
			for (FlightReservationDetailsDTO detailDTO : reservationDTO.getFlights()) {
				Flight f = flightRepository.findById(detailDTO.getFlightId()).get();
				r.getFlights().add(f);
				f.getReservations().add(r);
				f.getAirline().getReservations().add(r);
				r.getAirlines().add(f.getAirline());
				
				
				for (PassengerDTO passengerDTO : detailDTO.getPassengers()) {
					Seat s = findSeat(f, passengerDTO.getSeatNumber());
					s.setAvailable(false);
					r.setPrice(r.getPrice() + calculatePrice(s, f));
					Passenger p= new Passenger(passengerDTO, s);
					r.getPassengers().add(p);
					s.setPassenger(p);
				}
				
				
				for (FlightInviteDTO inviteDTO : detailDTO.getInvites()) {
					Seat s = findSeat(f, inviteDTO.getSeatNumber());
					s.setAvailable(false);
					if (!invites.containsKey(inviteDTO.getFriendEmail())) 
						invites.put(inviteDTO.getFriendEmail(), new ArrayList<>());
					invites.get(inviteDTO.getFriendEmail()).add(s.getId());
				}
			}
			
			createInvites(invites, reservationDTO.getFlights().get(0).getFlightId(), r);
			owner.setFlightBonusPoints(owner.getFlightBonusPoints() + 100);
			r = repository.save(r);
			try { repository.flush(); } 
			catch (OptimisticLockingFailureException ex) { return null; }
			return new FlightReservationDTO(r);
		}
		return null;
	}
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	private void createInvites(HashMap<String, ArrayList<Long>> invites, Long firstFlightId, FlightReservation r) {
		Flight f = flightRepository.findById(firstFlightId).get();
		Date d = f.getTakeoffTime();
		for (String invite : invites.keySet()) {
			User u = userRepository.findByEmail(invite);
			FlightInvite i = new FlightInvite(r, u, d);
			u.getFlightInvites().add(i);
			r.getInvites().add(i);
			i.getSeatIds().addAll(invites.get(invite));
			flightInviteRepository.save(i);
			sendInviteEmail(i);
		}
	}
	
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean acceptInvite(Long inviteId, String passport) {
		AbstractUser au = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userRepository.findByEmail(au.getEmail());
		
		if (verifyAcceptInvite(inviteId, u)) {
			FlightInvite invite = flightInviteRepository.findById(inviteId).get();
			invite.setAccepted(true);
			FlightReservation r = invite.getReservation();
			for (Long id : invite.getSeatIds()) {
				Optional<Seat> opt = seatRepository.findById(id);
				if (opt.isPresent()) {
					Seat s = opt.get();
					Passenger p = new Passenger(u.getFirstName(), u.getLastName(), passport, s);
					s.setPassenger(p);
					invite.setAccepted(true);
					r.setPrice(r.getPrice() + calculatePrice(s, s.getFlight()) - ( (float) 0.1 * u.getFlightBonusPoints()));
					seatRepository.save(s);
					repository.save(r);
				}
			}
			flightInviteRepository.save(invite);
			return true;
		}
		return false;
	}
	
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean declineInvite(Long inviteId) {
		AbstractUser au = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userRepository.findByEmail(au.getEmail());
		if (verifyDeclineInvite(inviteId, u)) {
			FlightInvite invite = flightInviteRepository.findById(inviteId).get();
			for (Long id : invite.getSeatIds()) {
				Seat s = seatRepository.findById(id).get();
				s.setAvailable(true);
				seatRepository.save(s);
			}
			FlightReservation r = invite.getReservation();
			r.getInvites().remove(invite);
			repository.save(r);
			flightInviteRepository.delete(invite);
			return true;
		}
		return false;
	}
	
	
	
	public List<FlightInviteDetailsDTO> getInvites() {
		AbstractUser au = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userRepository.findByEmail(au.getEmail());
		List<FlightInviteDetailsDTO> invites = new ArrayList<>();
		for (FlightInvite invite : u.getFlightInvites())
			if (!invite.isAccepted())
				invites.add(new FlightInviteDetailsDTO(invite));
		return invites;
	}
	
	
	
	private boolean verifyDeclineInvite(Long inviteId, User user) {
		Optional<FlightInvite> opt = flightInviteRepository.findById(inviteId);
		if (!opt.isPresent())
			return false;
		
		FlightInvite i = opt.get();
		if (i.getUser().getId() != user.getId())
			return false;
		
		Date now = new Date();
		if (now.after(i.getExpirationDate()) || now.after(i.getFlightStart()))
			return false;
		
		return true;
	}
	
	
	
	private void sendInviteEmail(FlightInvite invite) {
		String subject = "New trip invite!";
		String template = "You have new invite by %s (invite %d) check it here: http://localhost:8080/html/manageInvites.html";
		String text = String.format(template, invite.getReservation().getOwner().getEmail(),
				invite.getId());
		emailService.sendEmail("ivkovicdjordje1997@gmail.com", subject, text);
	}

	
	
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

	
	
	private boolean verifyAcceptInvite(Long inviteId, User u) {
		Optional<FlightInvite> opt = flightInviteRepository.findById(inviteId);
		if (!opt.isPresent())
			return false;
		
		FlightInvite invite = opt.get();
		Date now = new Date();
		
		if (!(now.before(invite.getExpirationDate()) && now.before(invite.getFlightStart())))
			return false;
		
		if (invite.isAccepted())
			return false;
		
		if (new Date().after(invite.getExpirationDate()))
			return false;
		
		if (invite.getUser().getId() != u.getId())
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
	
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean cancel(Long id) {
		FlightReservation fr = repository.findById(id).get();
		
		if (fr != null) {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date minDate = new Date();
			try {
				minDate = sdf.parse("2050-05-05");
			} catch (ParseException e) {
				System.err.println("CANCEL RESERVATION DATE CONVERSION FAILED.");
			}
			for (Flight f : fr.getFlights()) {
				Date takeoffTime = f.getTakeoffTime();
				if (takeoffTime.before(minDate)) {
					minDate = takeoffTime;
				}
			}
			// difference between now (cancellation time) and reservation start time in hours
			long diff = (minDate.getTime() - now.getTime()) / (60 * 60 * 1000);
			// reservation cannot be cancelled less than two days before the reservation starts
			if (diff < 3) return false;
			
			fr.setConfirmed(false);
			repository.save(fr);
			return true;
		}
		return false;
	}
	
	

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public boolean createQuickReservation(Long id, Integer seatNum, Float discount) {
		if (verifyCreateQR(id, seatNum)) {
			AirlineAdmin admin = ((AirlineAdmin) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			Airline a = airlineRepository.findByName(admin.getAirline().getName());
			
			Flight f = flightRepository.findById(id).get();
			FlightReservation r = new FlightReservation(new Date(), (float)0, null, true);
			r.getAirlines().add(a);
			a.getReservations().add(r);
			r.setDiscount(discount);
			r.getFlights().add(f);
			f.getReservations().add(r);
			
			Seat s = findSeat(f, seatNum);
			s.setAvailable(false);
			s.setQuickAvailable(true);
			Passenger p = new Passenger();
			p.setSeat(s);
			r.getPassengers().add(p);
			r.setPrice(calculatePrice(s, f));
			repository.save(r);
			
			try { repository.flush(); } catch(OptimisticLockingFailureException ex ) { return false; }
			return true;
		}
		return false;
	}
	
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public FlightReservationDTO takeQR(Long reservationId, String passport) {
		User u = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if (verifyTakeQR(reservationId, u.getEmail(), passport)) {
			User owner = userRepository.findByEmail(u.getEmail());
			FlightReservation r = repository.findById(reservationId).get();
			r.setOwner(owner);
			r.setDateOfReservation(new Date());
			owner.getFlightReservations().add(r);
			Passenger p = (Passenger) r.getPassengers().toArray()[0];
			p.getSeat().setQuickAvailable(false);
			p.setName(owner.getFirstName());
			p.setSurname(owner.getLastName());
			p.setPassport(passport);
			repository.save(r);
			
			try { repository.flush(); } catch(OptimisticLockingFailureException ex ) { return null; }
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
