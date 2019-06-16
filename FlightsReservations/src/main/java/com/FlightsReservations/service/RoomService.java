package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.dto.HotelDTO;
import com.FlightsReservations.domain.dto.RoomDTO;
import com.FlightsReservations.domain.dto.SearchRoomDTO;
import com.FlightsReservations.domain.enums.RoomType;
import com.FlightsReservations.repository.RoomRepository;

@Service
public class RoomService
{
	@Autowired
	RoomRepository repository;
	
	public List<RoomDTO> search(SearchRoomDTO searchDTO){
		ArrayList<Room> rooms = (ArrayList<Room>) this.repository.findAll();
		ArrayList<RoomDTO> results = new ArrayList<RoomDTO>();

		for(int i = 0;i<rooms.size();i++) {
			
			if(! (RoomType.valueOf(searchDTO.getType() )==rooms.get(i).getType() ) && !searchDTO.getType().equals("")){
				continue;
			}

			if(!searchDTO.getFloor().equals("") && Integer.parseInt(searchDTO.getFloor()) != rooms.get(i).getFloor()){
				continue;
			}
			
			if(!searchDTO.getNumberOfGuests().equals("") && Integer.parseInt(searchDTO.getNumberOfGuests()) != rooms.get(i).getNumberOfGuests())
			{
				continue;
			}
			
			if(!searchDTO.getOverNightStay().equals("") && Float.parseFloat(searchDTO.getOverNightStay()) != rooms.get(i).getOverNightStay()){
				continue;
			}
			
			if(!searchDTO.getOverallRating().equals("") && Float.parseFloat(searchDTO.getOverallRating()) != rooms.get(i).getAverageScore()){
				continue;
			}
			results.add(this.createDTO( (rooms.get(i) ) ) );
		}
		return results;
	}

	private RoomDTO createDTO(Room room)
	{
		RoomDTO dto = new RoomDTO(//int number, int floor, int numberOfGuests, String type, float averageScore, double overNightStay, String hotelName, int numberOfVotes
									room.getNumber(),
									room.getFloor(),
									room.getNumberOfGuests(),
									room.getType().toString(),
									room.getAverageScore(),
									room.getOverNightStay(),
									room.getHotel().getName(),
									room.getNumberOfVotes());
		return dto;
	}

}
