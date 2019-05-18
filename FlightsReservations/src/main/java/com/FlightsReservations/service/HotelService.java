package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.HotelReservation;
import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.dto.HotelDTO;
import com.FlightsReservations.domain.dto.HotelReservationDTO;
import com.FlightsReservations.repository.HotelRepository;
import com.FlightsReservations.repository.RoomRepository;

@Service
public class HotelService {

	@Autowired
	private HotelRepository repository;
	
	@Autowired
	private RoomRepository roomRepository;

	public HotelDTO create(HotelDTO t) {
		Hotel a = repository.findByName(t.getName());
		if (a == null) {
			a = new Hotel(
					t.getName(), 
					t.getLongitude(), 
					t.getLatitude(), 
					t.getPromoDescription(), 
					t.getAverageScore(), t.getNumberOfVotes());
			repository.save(a);
			return createDTO(a);
		}
		return null;
	}

	public boolean update(HotelDTO t) {
		Hotel h = repository.findByName(t.getName());
		if (h != null) {
			h.setName(t.getName());
			h.setLongitude(t.getLongitude());
			h.setLatitude(t.getLatitude());
			h.setPromoDescription(t.getPromoDescription());
			h.setAverageScore(t.getAverageScore());
			h.setNumberOfVotes(t.getNumberOfVotes());
			repository.save(h);
			return true;
		}
		return false;
	}

	public HotelDTO findOne(String name) {
		Hotel a = repository.findByName(name);
		if (a != null)
			return createDTO(a);
		return null;
	}
	
	public List<HotelDTO> findAll() {
		List<HotelDTO> dtos = new ArrayList<>();
		for (Hotel a : repository.findAll()) 
			dtos.add(createDTO(a));
		return dtos;
	}	
	
	
	public HotelDTO addRoom(String hotelName, String roomName) {
		Hotel hotel = repository.findByName(hotelName);
		Room room = roomRepository.findByName(roomName);
		
		if (hotel != null && room != null) {
			hotel.getRoomConfiguration().add(room);
			repository.save(hotel);
			return createDTO(hotel);
		}
		return null;
	}
	
	
	private HotelDTO createDTO(Hotel hotel) {
		HotelDTO dto = new HotelDTO(hotel);
		for (Room a : hotel.getRoomConfiguration()) 
			dto.getRooms().add(a.getName());
		for (HotelReservation r : hotel.getReservations())
			dto.getReservations().add(new HotelReservationDTO(r));
		
		return dto;
	}
	/*
	public boolean addRoom(RoomDTO room) {
		Long hotelID = room.getHotel_id();
		Hotel hotel = findOne(hotelID);
		
		if (hotel != null) {
			Room r = new Room(
					room.getOverallRating(),
					room.getOverNightStay(),
					hotel);
			hotel.getRoomConfiguration().add(r);
			repository.save(hotel);
			return true;
		}
		return false;
	}
	*/
}
