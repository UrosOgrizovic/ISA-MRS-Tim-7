package com.FlightsReservations.scheduler;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;

import com.FlightsReservations.domain.FlightInvite;
import com.FlightsReservations.domain.Seat;
import com.FlightsReservations.repository.FlightInviteRepository;
import com.FlightsReservations.repository.SeatRepository;

@Controller
public class FlightInviteScheduler {
	
	@Autowired
	private FlightInviteRepository repo;
	
	@Autowired
	private SeatRepository seatRepo;
	
	@Scheduled(cron = "${flightInvite.cron}")
	public void job() {
		System.out.println("flight invites cron job...");
		List<FlightInvite> invites = repo.findAll();
		Date now = new Date();
		for (FlightInvite invite : invites) {
			if (!(now.before(invite.getExpirationDate()) && now.before(invite.getFlightStart()))) {
				for (Long id : invite.getSeatIds()) {
					Seat s = seatRepo.findById(id).get();
					s.setAvailable(false);
					seatRepo.save(s);
				}
				repo.delete(invite);
			}
		}
	}
}
