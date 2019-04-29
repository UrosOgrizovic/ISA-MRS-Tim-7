package com.FlightsReservations.service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.RoomReservation;
import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.RoomReservationDTO;
import com.FlightsReservations.domain.dto.RoomReservationRequestDTO;
import com.FlightsReservations.repository.RoomRepository;
import com.FlightsReservations.repository.RoomReservationRepository;
import com.FlightsReservations.repository.UserRepository;

@Service
public class RoomReservationService {
	@Autowired
	private RoomReservationRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	public RoomReservationDTO create(RoomReservationRequestDTO dto) {
		if (!creatingSemanticValidation(dto))
			return null;
		
		Date startTime = dto.getStartTime();
		Date endTime = dto.getEndTime();
		
		int reservationDurationHours = (int) ( (endTime.getTime() - startTime.getTime() ) / 3600000 );
		int reservationDurationDays = reservationDurationHours / 24;
		User owner = userRepository.findByEmail(dto.getOwnerEmail());
		Room room = roomRepository.findById(dto.getRoomId()).get();
		
		Float total = (float) room.getOverNightStay() * reservationDurationDays;
		Float discount = dto.getDiscount();
		
		total = total - total*discount / 100;
		
		RoomReservation reservation = new RoomReservation(new Date(), discount, total, (Boolean) true, owner, dto.getRoomId(), startTime, endTime);
		
		reservation = repository.save(reservation);
		return new RoomReservationDTO(reservation);
	}
	
	private boolean creatingSemanticValidation(RoomReservationRequestDTO dto) {
		// user with given email must exist
		if (userRepository.findByEmail(dto.getOwnerEmail()) == null)
			return false;
		
		// room with given id must exist
		if (roomRepository.findById(dto.getRoomId()) == null)
			return false;
		
		Date startTime = dto.getStartTime();
		Date endTime = dto.getEndTime();
		
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTime(startTime);
		
		// only on the hour (= top of the hour) times are accepted, e.g. 08:00, 12:00 etc.
		if (!(calendar.get(Calendar.MINUTE) == 0) ) 
			return false;
		
		calendar.setTime(endTime);

		if (!(calendar.get(Calendar.MINUTE) == 0) ) 
			return false;
		
		// start time has to be before end time
		if (startTime.after(endTime))
			return false;
		
		//TODO: check that there are no other reservations for room id via transactions		
		return true;
	}
	
	public boolean cancel(Long id) {
		RoomReservation rr = repository.findById(id).get();
		if (rr != null) {
			Date now = new Date();
			// difference between now (cancellation time) and reservation start time in days
			long diff = (rr.getStartTime().getTime() - now.getTime()) / (24 * 60 * 60 * 1000);
			// reservation cannot be cancelled less than two days before the reservation starts
			if (diff < 2) return false;
			rr.setConfirmed(false);
			repository.save(rr);
			return true;
		}
		return false;
	}
}
