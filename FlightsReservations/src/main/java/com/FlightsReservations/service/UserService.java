package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.RoomReservation;
import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.CarReservationDTO;
import com.FlightsReservations.domain.dto.RegistrationUserDTO;
import com.FlightsReservations.domain.dto.RoomReservationDTO;
import com.FlightsReservations.domain.dto.UserDTO;
import com.FlightsReservations.repository.CarReservationRepository;
import com.FlightsReservations.repository.RoomReservationRepository;
import com.FlightsReservations.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private CarReservationRepository carReservationRepository;
	
	@Autowired
	private RoomReservationRepository roomReservationRepository;
	
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
	
	public List<UserDTO> getFriends(String email) {
		
		User u = repository.findByEmail(email);
		
		List<User> friends = repository.findFriends(u.getId());
		List<UserDTO> friendsDTO = new ArrayList<UserDTO>();
		for (User user : friends) {
			if (user.isEnabled()) {
				UserDTO udto = new UserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getAddress(), true);
				friendsDTO.add(udto);
			}
		}
		return friendsDTO;
	}
	
	public void addFriend(Long userId, Long friendId) {
		repository.addFriend(userId, friendId);
	}
	
	public List<CarReservationDTO> getCarReservations(String email) {
		User u = repository.findByEmail(email);
		Collection<CarReservation> carReservations = carReservationRepository.findCarReservationsOfUser(u.getId());
		List<CarReservationDTO> carReservationsDTO = new ArrayList<CarReservationDTO>();
		for (CarReservation cr : carReservations) {
			CarReservationDTO crdto = new CarReservationDTO(cr);
			carReservationsDTO.add(crdto);
		}
		return carReservationsDTO;
	}
	
	public List<RoomReservationDTO> getRoomReservations(String email) {
		User u = repository.findByEmail(email);
		Collection<RoomReservation> roomReservations = roomReservationRepository.findRoomReservationsOfUser(u.getId());
		List<RoomReservationDTO> roomReservationsDTO = new ArrayList<RoomReservationDTO>();
		for (RoomReservation rr : roomReservations) {
			RoomReservationDTO rrdto = new RoomReservationDTO(rr);
			roomReservationsDTO.add(rrdto);
		}
		return roomReservationsDTO;
		
	}
}
