package com.FlightsReservations.service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.AirlineAdmin;
import com.FlightsReservations.domain.dto.AirlineAdminDTO;
import com.FlightsReservations.repository.AirlineAdminRepository;
import com.FlightsReservations.repository.AuthorityRepository;
import com.FlightsReservations.repository.UserRepository;

@Service
public class AirlineAdminService {
	
	@Autowired
	AirlineAdminRepository repository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	AuthorityRepository authRepository;
	
	
	public AirlineAdminDTO create(AirlineAdminDTO dto) {
		AirlineAdmin a = repository.findByEmail(dto.getEmail());
		if (a == null) {
			a = new AirlineAdmin(dto);
			a.setAuthorities(authRepository.findAll());
			
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
	
	
	private AirlineAdminDTO createDTO(AirlineAdmin a) {
		AirlineAdminDTO dto = new AirlineAdminDTO(a);
		return dto;
	}


	public AirlineAdminDTO getAdminFromContext() {
		AirlineAdmin admin = (AirlineAdmin) SecurityContextHolder.getContext().getAuthentication();
		return new AirlineAdminDTO(admin);
	}
	
}
