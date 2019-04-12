package com.FlightsReservations.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.repository.HotelRepository;

@Service
public class HotelService {

	@Autowired
	private HotelRepository repository;
	
	public Hotel create(Hotel t) {
		return repository.save(t);
	}

	public boolean update(Hotel t) {
		Hotel h = findOne(t.getId());
		if (h != null) {
			h.setName(t.getName());
			h.setLongitude(t.getLongitude());
			h.setLongitude(t.getLatitude());
			h.setPromoDescription(t.getPromoDescription());
			h.setRoomConfiguration(t.getRoomConfiguration());
			h.setAverageScore(t.getAverageScore());
			h.setPricelist(t.getPricelist());
			h.setRoomConfiguration(t.getRoomConfiguration());
			repository.save(h);
			return true;
		}
		return false;
	}

	public Hotel findOne(Long id) {
		try {
			return repository.findById(id).get();
		} catch (NoSuchElementException e) {
			return null;
		}
	}

	public List<Hotel> findAll() {
		return repository.findAll();
	}
	
	//public Hotel rate(Long id, float score) {
	//	Hotel hotel = findOne(id);
	//	if (hotel != null) {
	//		float newAvgScore = hotel.getAverageScore() * hotel.getNumberOfVotes() + score;
	//		int newNumberOfVotes = hotel.getNumberOfVotes() + 1;
	//		hotel.setNumberOfVotes(newNumberOfVotes);
	//		hotel.setAverageScore(newAvgScore / newNumberOfVotes);
	//		repository.save(hotel);
	//		return hotel;
	//	}
	//	return null;
	//}
}
