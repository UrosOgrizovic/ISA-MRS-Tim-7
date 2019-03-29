package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.RegistrationUserDTO;
import com.FlightsReservations.domain.dto.UserDTO;
import com.FlightsReservations.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	public UserDTO create(RegistrationUserDTO t) {
		if (repository.findByEmail(t.getEmail()) == null) {
			User u = new User();
			u.setFirstName(t.getFirstName());
			u.setLastName(t.getLastName());
			u.setEmail(t.getEmail());
			u.setAddress(t.getAddress());
			u.setPhone(t.getPhone());
			u.setPassword(t.getPassword());
			repository.save(u);
			return new UserDTO(u);
		}
		return null;
	}

	public boolean update(UserDTO t) {
		User u = repository.findByEmail(t.getEmail());
		if (u != null) {
			u.setFirstName(t.getFirstName());
			u.setLastName(t.getLastName());
			u.setPhone(t.getPhone());
			u.setAddress(t.getAddress());
			repository.save(u);
			return true;
		}
		return false;
	}

	public UserDTO findOne(String email) {
		User u = repository.findByEmail(email);
		if (u != null)
			return new UserDTO(u);
		return null;
	}

	public List<UserDTO> findAll() {
		List<UserDTO> dtos = new ArrayList<>();
		for (User u : repository.findAll())
			dtos.add(new UserDTO(u));
		return dtos;
	}
	
	public List<RegistrationUserDTO> findAllRegistrationUsers() {
		List<RegistrationUserDTO> rdtos = new ArrayList<>();
		for (User u : repository.findAll()) {
			rdtos.add(new RegistrationUserDTO(u));
		}
		return rdtos;
	}
}
