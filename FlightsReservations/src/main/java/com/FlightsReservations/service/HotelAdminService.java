package com.FlightsReservations.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.HotelAdmin;
import com.FlightsReservations.domain.dto.HotelAdminDTO;
import com.FlightsReservations.repository.HotelAdminRepository;

@Component
public class HotelAdminService {

	
	@Autowired
	HotelAdminRepository repository;//TODO: will be deleted
	
	
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
			repository.save(a);
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
		//Airline al = a.getAirline();
		//dto.setAirline(al.getName());
	return dto;
	}

}
