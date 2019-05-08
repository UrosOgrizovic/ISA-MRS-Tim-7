package com.FlightsReservations.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.RACSAdmin;
import com.FlightsReservations.domain.dto.RACSAdminDTO;
import com.FlightsReservations.repository.RACSAdminRepository;

@Component
public class RACSAdminService {

	
	@Autowired
	RACSAdminRepository repository;//TODO: will be deleted
	
	
	public RACSAdminDTO create(RACSAdminDTO dto) {
		RACSAdmin a = repository.findByEmail(dto.getEmail());
		if (a == null) {
			a = new RACSAdmin(dto);
			repository.save(a);
			return createDTO(a);
		}
		return null;

	}

	public boolean update(RACSAdminDTO dto) {
		RACSAdmin a = repository.findByEmail(dto.getEmail());
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

	public RACSAdminDTO findOne(String email) {
		// TODO Auto-generated method stub
		RACSAdmin a = repository.findByEmail(email);
		if(a != null)
			return createDTO(a);
		return null;
	}

	public void delete(String email) {
		// TODO Auto-generated method stub
				
	}

	public Collection<RACSAdminDTO> findAll() {
		List<RACSAdmin> admins = repository.findAll();
		Set<RACSAdminDTO> dtos = new HashSet<>();

		for (RACSAdmin a : admins)
			dtos.add(createDTO(a));
		return dtos;
	}
	
	private RACSAdminDTO createDTO(RACSAdmin a)
	{
		RACSAdminDTO dto = new RACSAdminDTO(a);
		//Airline al = a.getAirline();
		//dto.setAirline(al.getName());
	return dto;
	}

}
