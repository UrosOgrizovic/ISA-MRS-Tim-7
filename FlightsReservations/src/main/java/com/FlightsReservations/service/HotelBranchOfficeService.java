package com.FlightsReservations.service;

import java.util.Collection;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.HotelBranchOffice;
import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.dto.HotelBranchOfficeDTO;
import com.FlightsReservations.domain.dto.RoomDTO;
import com.FlightsReservations.repository.HotelBranchOfficeRepository;
import com.FlightsReservations.repository.RoomRepository;

@Service
public class HotelBranchOfficeService {

	@Autowired
	private HotelBranchOfficeRepository hotelBranchOfficeRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	public HotelBranchOffice create(HotelBranchOfficeDTO dto) {
		
		HotelBranchOffice hbo = new HotelBranchOffice(dto);
		return hotelBranchOfficeRepository.save(hbo);
	}
	
	public boolean update(HotelBranchOffice hbo) {
		HotelBranchOffice hotelbo = findOne(hbo.getId());
		if (hotelbo != null) {
			hotelbo.setLatitude(hbo.getLatitude());
			hotelbo.setLongitude(hbo.getLongitude());
			hotelbo.setRoomConfiguration(hbo.getRoomConfiguration());
			hotelbo.setName(hbo.getName());
			hotelbo.setCompany(hbo.getCompany());
			hotelBranchOfficeRepository.save(hotelbo);
			return true;
		}
		return false;
	}
	
	public boolean addRoom(RoomDTO dto) {
		HotelBranchOffice hbo = dto.getHotelBranchOffice();
		
		if (hbo != null) {
			Room room = new Room(
					dto.getNumber(),
					dto.getNumberOfGuests(),
					dto.getType(),
					dto.getAverageScore(),
					dto.getOverNightStay(),
					hbo,
					dto.getNumberOfVotes());
			hbo.getRoomConfiguration().add(room);
			roomRepository.save(room);
			hotelBranchOfficeRepository.save(hbo);
			return true;
		}
		return false;
	}
	
	public HotelBranchOffice findOne(Long id) {
		try {
			return hotelBranchOfficeRepository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public Collection<HotelBranchOffice> findByName(String name) {
		try {
			return hotelBranchOfficeRepository.findByName(name);
		} catch (NoSuchElementException e) {
			return null;
		}
	}
	
	public void delete(Long id) {
		hotelBranchOfficeRepository.deleteById(id);
	}

	public Collection<HotelBranchOffice> findAll() {
		return hotelBranchOfficeRepository.findAll();
	}

}
