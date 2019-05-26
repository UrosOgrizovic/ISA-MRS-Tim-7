package com.FlightsReservations.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.SystemAdmin;
import com.FlightsReservations.domain.dto.SystemAdminDTO;
import com.FlightsReservations.domain.enums.AdminType;
import com.FlightsReservations.repository.SystemAdminRepository;

@Component
public class SystemAdminService {

	
	@Autowired
	SystemAdminRepository repository;//TODO: will be deleted
	
	
	public SystemAdminDTO create(SystemAdminDTO dto) {
		SystemAdmin a = repository.findByEmail(dto.getEmail());
		if (a == null) {
			a = new SystemAdmin(dto);
			repository.save(a);
			return createDTO(a);
		}
		return null;

	}

	public boolean update(SystemAdminDTO dto) {
		SystemAdmin a = repository.findByEmail(dto.getEmail());
		if (a != null) {
			a.setFirstName(dto.getFirstName() );
			a.setLastName(dto.getLastName());
			a.setEmail(dto.getEmail());
			a.setPassword(dto.getPassword());
			a.setPhone(dto.getPhone());
			a.setAddress(dto.getAddress());
			a.setType(AdminType.SYSTEM);
			repository.save(a);
			return true;
		}
		return false;
	}

	public SystemAdminDTO findOne(String email) {
		// TODO Auto-generated method stub
		SystemAdmin a = repository.findByEmail(email);
		if(a != null)
			return createDTO(a);
		return null;
	}

	public void delete(String email) {
		// TODO Auto-generated method stub
				
	}

	public Collection<SystemAdminDTO> findAll() {
		List<SystemAdmin> admins = repository.findAll();
		Set<SystemAdminDTO> dtos = new HashSet<>();

		for (SystemAdmin a : admins)
			dtos.add(createDTO(a));
		return dtos;
	}
	
	private SystemAdminDTO createDTO(SystemAdmin a)
	{
		SystemAdminDTO dto = new SystemAdminDTO(a);
		//Airline al = a.getAirline();
		//dto.setAirline(al.getName());
	return dto;
	}

}
