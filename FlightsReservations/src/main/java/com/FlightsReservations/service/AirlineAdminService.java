package com.FlightsReservations.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.AirlineAdmin;
import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.AirlineAdminDTO;
import com.FlightsReservations.domain.dto.AirlineDTO;
import com.FlightsReservations.repository.AirlineAdminRepository;
import com.FlightsReservations.repository.UserRepository;

@Component
public class AirlineAdminService {

	
	@Autowired
	AirlineAdminRepository repository;//TODO: will be deleted
	
	@Autowired
	UserRepository userRepository;
	
	public AirlineAdminDTO create(AirlineAdminDTO dto) {
		AirlineAdmin a = repository.findByEmail(dto.getEmail());
		if (a == null) {
			a = new AirlineAdmin(dto);
			repository.save(a);
			return createDTO(a);
		}
		return null;
	}

	public boolean update(AirlineAdminDTO dto) {
		AirlineAdmin a = repository.findByEmail(dto.getEmail());
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

	public AirlineAdminDTO findOne(String email) {
		// TODO Auto-generated method stub
		AirlineAdmin a = repository.findByEmail(email);
		if(a != null)
			return createDTO(a);
		return null;
	}

	public void delete(String email) {
		// TODO Auto-generated method stub
				
	}

	public Collection<AirlineAdminDTO> findAll() {
		List<AirlineAdmin> admins = repository.findAll();
		Set<AirlineAdminDTO> dtos = new HashSet<>();

		for (AirlineAdmin a : admins)
			dtos.add(createDTO(a));
		return dtos;
	}
	
	private AirlineAdminDTO createDTO(AirlineAdmin a)
	{
		AirlineAdminDTO dto = new AirlineAdminDTO(a);
		//Airline al = a.getAirline();
		//dto.setAirline(al.getName());
	return dto;
	}

	public AirlineDTO getAirline(@NotBlank String adminEmail) {
		User u = userRepository.findByEmail(adminEmail);
		AirlineAdmin aa = repository.findById(u.getId()).get();
		return new AirlineDTO(aa.getAirline());
	}

}
