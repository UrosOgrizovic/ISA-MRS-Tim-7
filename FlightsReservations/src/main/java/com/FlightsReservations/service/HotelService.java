package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.HotelAdmin;
import com.FlightsReservations.domain.HotelPricelistItem;
import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.dto.HotelDTO;
import com.FlightsReservations.domain.dto.RoomDTO;
import com.FlightsReservations.domain.dto.SearchHotelDTO;
import com.FlightsReservations.repository.HotelAdminRepository;
import com.FlightsReservations.repository.HotelRepository;
import com.FlightsReservations.repository.RoomRepository;

@Service
public class HotelService {

	@Autowired
	private HotelRepository repository;
	
	@Autowired
	private HotelAdminService adminService;
	
	@Autowired
	private HotelAdminRepository adminRepository;
	
	@Autowired
	private RoomRepository roomRepository;

	public HotelDTO create(HotelDTO t) {
		Hotel h = repository.findByName(t.getName());//TODO: check if already exists
		if (h == null) {
			h = new Hotel(
					t.getName(), 
					t.getLongitude(), 
					t.getLatitude(), 
					t.getCity(),
					t.getState(),
					t.getPromoDescription(),
					t.getAverageScore(), t.getNumberOfVotes());
			HotelAdmin ha = null;
			if(t.getHotelAdminEmail()!="" && t.getHotelAdminEmail()!=null)
			{
				ha = adminRepository.findByEmail(t.getHotelAdminEmail() );
				if(ha!=null)
				{
					
					ha.setHotel(h);
					adminService.update(ha);
					h.setAdmin(ha);
				}
			}
			
			repository.save(h);
			
			return createDTO(h);
		}
		return null;
	}
	
	public List<HotelDTO> search(SearchHotelDTO searchDTO){
		ArrayList<Hotel> hotels = (ArrayList<Hotel>) this.repository.findAll();
		ArrayList<HotelDTO> results = new ArrayList<HotelDTO>();

		for(int i = 0;i<hotels.size();i++) {
			
			if(!searchDTO.getName().equalsIgnoreCase(hotels.get(i).getName()) && !searchDTO.getName().equals("")){
				continue;
			}
			
			if(!searchDTO.getCity().equalsIgnoreCase(hotels.get(i).getCity()) && !searchDTO.getCity().equals("")){
				continue;
			}
			
			if(!searchDTO.getState().equalsIgnoreCase(hotels.get(i).getState()) && !searchDTO.getState().equals("")){
				continue;
			}
			
			if(!searchDTO.getAverageScore().equals("") && Float.parseFloat(searchDTO.getAverageScore()) > hotels.get(i).getAverageScore()){
				continue;
			}
			results.add(this.createDTO( (hotels.get(i) ) ) );
		}
		return results;
	}

	public boolean update(HotelDTO t) {
		Hotel h = repository.findByName(t.getName());
		if (h != null) {
			h.setName(t.getName());
			h.setLongitude(t.getLongitude());
			h.setLatitude(t.getLatitude());
			h.setCity(t.getCity());
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
	
	/*
	public HotelDTO addRoom(String hotelName, String roomName) {
		Hotel hotel = repository.findByName(hotelName);
		Room room = roomRepository.findByHotelId(roomName);
		
		if (hotel != null && room != null) {
			hotel.getRoomConfiguration().add(room);
			repository.save(hotel);
			return createDTO(hotel);
		}
		return null;
	}
	*/
	
	private HotelDTO createDTO(Hotel hotel) {
		HotelDTO dto = new HotelDTO(hotel);
		if(hotel.getRoomConfiguration()!=null && !hotel.getRoomConfiguration().isEmpty() )
		{
			for (HashMap.Entry<Integer, HashSet<Room>> entry : hotel.getRoomConfiguration().entrySet() )
			{
				dto.getRoomConfiguration().put(entry.getKey(), entry.getValue());
			}
		}
		if(hotel.getPricelist()!=null && !hotel.getPricelist().isEmpty()) for (HotelPricelistItem pli : hotel.getPricelist())
			dto.getPricelist().add(pli);
		
		return dto;
	}
	
	public boolean addRoom(RoomDTO dto) {
		Hotel hotel = repository.findByName(dto.getHotelName() );
		
		if (hotel != null) { //int number, int numberOfGuests, String type, float averageScore, double overNightStay, Hotel hotel, int numberOfVotes
			Room room = new Room(
					dto.getNumber(),
					dto.getNumberOfGuests(),
					dto.getType(),
					dto.getAverageScore(),
					dto.getOverNightStay(),
					dto.getFloor(),
					hotel,
					dto.getNumberOfVotes());
			
			hotel.getRoomConfiguration().get(room.getFloor()).add(room);
			roomRepository.save(room);
			repository.save(hotel);
			return true;
		}
		return false;
	}
}
