package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.FlightsReservations.domain.RACSAdmin;
import com.FlightsReservations.domain.dto.RACSAdminDTO;
import com.FlightsReservations.repository.RACSAdminRepository;

@Component
@Transactional(readOnly = false)
public class RACSAdminService {

	
	@Autowired
	RACSAdminRepository racsAdminRepository;
	
	public RACSAdminDTO create(RACSAdminDTO dto) {
		RACSAdmin a = racsAdminRepository.findByEmail(dto.getEmail());
		if (a == null) {
			a = new RACSAdmin(dto);
			racsAdminRepository.save(a);
			return createDTO(a);
		}
		return null;
	}
	
	public void save(RACSAdmin admin) {
		racsAdminRepository.save(admin);
	}
	
	public boolean update(RACSAdminDTO dto) {
		RACSAdmin a = racsAdminRepository.findByEmail(dto.getEmail());
		if (a != null) {
			a.setFirstName(dto.getFirstName() );
			a.setLastName(dto.getLastName());
			a.setEmail(dto.getEmail());
			a.setPassword(dto.getPassword());
			a.setPhone(dto.getPhone());
			a.setAddress(dto.getAddress());
			a.setRACS(dto.getRacs());
			racsAdminRepository.save(a);
			return true;
		}
		return false;
	}

	public RACSAdminDTO findOne(String email) {
		RACSAdmin a = racsAdminRepository.findByEmail(email);
		if(a != null)
			return createDTO(a);
		return null;
	}

	public void delete(String email) {
		racsAdminRepository.deleteByEmail(email);
				
	}

	public List<RACSAdminDTO> findAll() {
		List<RACSAdmin> admins = racsAdminRepository.findAll();
		List<RACSAdminDTO> dtos = new ArrayList<RACSAdminDTO>();

		for (RACSAdmin a : admins)
			dtos.add(createDTO(a));
		return dtos;
	}
	
	private RACSAdminDTO createDTO(RACSAdmin a)
	{
		RACSAdminDTO dto = new RACSAdminDTO(a);
		return dto;
	}
	
	public float getAverageRACSRating(String email) {
		RACSAdmin a = racsAdminRepository.findByEmail(email);
		
		if (a.getEmail() != null) {
			
			return a.getRACS().getAverageScore();
		}
		return 0;
	}
}
