package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.RegistrationUserDTO;
import com.FlightsReservations.domain.dto.UserDTO;
import com.FlightsReservations.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	public UserDTO create(RegistrationUserDTO t) {
		if (repository.findByEmail(t.getEmail()) == null) {
			User u = new User();
			u.setFirstName(t.getFirstName());
			u.setLastName(t.getLastName());
			u.setEmail(t.getEmail());
			u.setAddress(t.getAddress());
			u.setPhone(t.getPhone());
			u.setPassword(passwordEncoder.encode(t.getPassword()));
			u.setEnabled(true);
			repository.save(u);
			return new UserDTO(u);
		}
		return null;
	}

	public UserRepository getRepository() {
		return repository;
	}

	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

	public boolean update(UserDTO t) {
		User u = repository.findByEmail(t.getEmail());
		if (u != null) {
			u.setFirstName(t.getFirstName());
			u.setLastName(t.getLastName());
			u.setPhone(t.getPhone());
			u.setAddress(t.getAddress());
			u.setEnabled(t.isEnabled());
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
}
