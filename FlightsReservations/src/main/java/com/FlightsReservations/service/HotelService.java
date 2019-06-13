package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.BranchOffice;
import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.HotelBranchOffice;
import com.FlightsReservations.domain.HotelReservation;
import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.dto.HotelDTO;
import com.FlightsReservations.domain.dto.HotelReservationDTO;
import com.FlightsReservations.domain.dto.SearchHotelDTO;
import com.FlightsReservations.repository.HotelRepository;

@Service
public class HotelService {

	@Autowired
	private HotelRepository repository;
	
	public HotelDTO create(HotelDTO t) {
		Hotel a = repository.findByName(t.getName());//TODO: provjera da li vec postoji
		if (a == null) {
			a = new Hotel(
					t.getName(), 
					t.getLongitude(), 
					t.getLatitude(), 
					t.getCity(),
					t.getState(),
					t.getPromoDescription(),
					t.getAverageScore(), t.getNumberOfVotes());
			repository.save(a);
			return t;
		}
		return null;
	}
	
	public List<HotelDTO> search(SearchHotelDTO searchHotelDTO){
		ArrayList<Hotel> hotels = (ArrayList<Hotel>) this.repository.findAll();
		ArrayList<HotelDTO> results = new ArrayList<HotelDTO>();
		
		for(int i = 0;i<hotels.size();i++) {
			if(!searchHotelDTO.getName().equalsIgnoreCase(hotels.get(i).getName()) || !searchHotelDTO.getName().equals("")){
				continue;
			}
			
			if(!searchHotelDTO.getCity().equalsIgnoreCase(hotels.get(i).getCity()) || !searchHotelDTO.getCity().equals("")){
				continue;
			}
			
			if(!searchHotelDTO.getState().equalsIgnoreCase(hotels.get(i).getState()) || !searchHotelDTO.getState().equals("")){
				continue;
			}
			
			if(Float.parseFloat(searchHotelDTO.getAverageScore()) > hotels.get(i).getAverageScore() || !searchHotelDTO.getAverageScore().equals("")){
				continue;
			}
			System.out.println("Usao u servis for 1");
			results.add(this.createDTO( (hotels.get(i) ) ) );
			System.out.println("Usao u servis for 2");
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
		Set<BranchOffice> hotelBranchOffices = hotel.getBranchOffices();
		HotelBranchOffice hbo = new HotelBranchOffice();
		
		for (BranchOffice bo : hotelBranchOffices) {
			hbo = (HotelBranchOffice) bo;
			if(hbo.getRoomConfiguration()!=null) for (Room a : hbo.getRoomConfiguration()) 
				dto.getRooms().add(a.getNumber());
		}
		
		if(hotel.getReservations()!=null) for (HotelReservation r : hotel.getReservations())
			dto.getReservations().add(new HotelReservationDTO(r));

		
		return dto;
	}
}
