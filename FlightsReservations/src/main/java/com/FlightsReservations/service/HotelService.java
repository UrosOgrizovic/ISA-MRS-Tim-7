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
			h.setAddress(t.getAddress());
			h.setPromoDescription(t.getPromoDescription());
			h.setServicesPriceList(t.getServicesPriceList());
			h.setRoomConfiguration(t.getRoomConfiguration());
			h.setOverallRating(t.getOverallRating());
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
}
