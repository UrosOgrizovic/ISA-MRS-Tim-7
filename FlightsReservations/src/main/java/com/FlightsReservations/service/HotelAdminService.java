package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.HotelAdmin;
import com.FlightsReservations.domain.dto.HotelAdminDTO;
import com.FlightsReservations.repository.HotelAdminRepository;
import com.FlightsReservations.repository.HotelRepository;

@Component
public class HotelAdminService {

	
	@Autowired
	HotelAdminRepository repository;//TODO: will be deleted
	
	@Autowired
	HotelRepository hotelRepository;
	
	
	public HotelAdminDTO create(HotelAdminDTO dto) {
		HotelAdmin a = repository.findByEmail(dto.getEmail());
		if (a == null) {
			a = new HotelAdmin(dto);
			repository.save(a);
			return createDTO(a);
		}
		return null;

	}

	public boolean update(HotelAdminDTO dto) {
		HotelAdmin a = repository.findByEmail(dto.getEmail());
		if (a != null) {
			a.setFirstName(dto.getFirstName() );
			a.setLastName(dto.getLastName());
			a.setEmail(dto.getEmail());
			a.setPassword(dto.getPassword());
			a.setPhone(dto.getPhone());
			a.setAddress(dto.getAddress());
			Hotel h = hotelRepository.findByName(dto.getHotelName() );
			a.setHotel(h);
			repository.save(a);
			return true;
		}
		return false;
		
	}
	
	public boolean update(HotelAdmin t) {
		HotelAdmin h = repository.findById(t.getId() ).get();
		if (h != null) {
			h.setEmail(t.getEmail() );
			h.setAddress(t.getAddress());
			h.setEnabled(t.isEnabled() );
			h.setFirstName(t.getFirstName() );
			h.setLastName(t.getLastName() );
			h.setHotel(t.getHotel() );
			h.setLastPasswordResetDate(t.getLastPasswordResetDate() );
			h.setPassword(t.getPassword() );
			h.setPhone(t.getPhone() );
			//h.setPicturePath(t.getPicturePath() );
			repository.save(h);
			return true;
		}
		return false;
	}

	public HotelAdminDTO findOne(String email) {
		// TODO Auto-generated method stub
		HotelAdmin a = repository.findByEmail(email);
		if(a != null)
			return createDTO(a);
		return null;
	}
	
	public List<HotelAdminDTO> lookupAll()
	{
		List<HotelAdmin> admins = repository.findAll();
		List<HotelAdminDTO> results = new ArrayList<HotelAdminDTO>();
		for(HotelAdmin admin : admins)
		{
			if(admin.getHotel()==null) results.add(createDTO(admin));//only admins who don't already have a hotel assigned
		}
		return results;
	}

	public void delete(String email) {
		// TODO Auto-generated method stub
				
	}

	public Collection<HotelAdminDTO> findAll() {
		List<HotelAdmin> admins = repository.findAll();
		Set<HotelAdminDTO> dtos = new HashSet<>();

		for (HotelAdmin a : admins)
			dtos.add(createDTO(a));
		return dtos;
	}
	
	private HotelAdminDTO createDTO(HotelAdmin a)
	{
		HotelAdminDTO dto = new HotelAdminDTO(a);
		if(a.getHotel()!=null) dto.setHotelName(a.getHotel().getName() );
		else dto.setHotelName("");
	return dto;
	}

}
