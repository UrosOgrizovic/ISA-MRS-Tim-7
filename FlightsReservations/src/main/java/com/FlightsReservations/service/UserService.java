package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Authority;
import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.FlightReservation;
import com.FlightsReservations.domain.FriendRequest;
import com.FlightsReservations.domain.RoomReservation;
import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.CarReservationDTO;
import com.FlightsReservations.domain.dto.FlightReservationDTO;
import com.FlightsReservations.domain.dto.FriendRequestDTO;
import com.FlightsReservations.domain.dto.RegistrationUserDTO;
import com.FlightsReservations.domain.dto.RoomReservationDTO;
import com.FlightsReservations.domain.dto.UserDTO;
import com.FlightsReservations.repository.AuthorityRepository;
import com.FlightsReservations.repository.CarReservationRepository;
import com.FlightsReservations.repository.FlightReservationRepository;
import com.FlightsReservations.repository.FriendRequestRepository;
import com.FlightsReservations.repository.RoomReservationRepository;
import com.FlightsReservations.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private FriendRequestRepository friendRequestsRepository;

	@Autowired
	private CarReservationRepository carReservationRepository;
	
	@Autowired
	private FlightReservationRepository flightReservationRepository;
	
	@Autowired
	private RoomReservationRepository roomReservationRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public UserDTO create(RegistrationUserDTO t) {
		if (repository.findByEmail(t.getEmail()) == null) {
			User u = new User();
			ArrayList<Authority> authorities = new ArrayList<Authority>();
			for (String a : t.getAuthorities()) {
				authorities.add(authorityRepository.findByName(a));
			}
			u.setFirstName(t.getFirstName());
			u.setLastName(t.getLastName());
			u.setEmail(t.getEmail());
			u.setAddress(t.getAddress());
			u.setPhone(t.getPhone());
			u.setPassword(passwordEncoder.encode(t.getPassword()));
			u.setEnabled(true);
			u.setAuthorities(authorities);
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
	
	
	public Optional<User> findById(Long id) {
		return repository.findById(id);
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
		List<UserDTO> friendsDTO = new ArrayList<>();
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
		List<CarReservationDTO> carReservationsDTO = new ArrayList<>();
		for (CarReservation cr : carReservations) {
			CarReservationDTO crdto = new CarReservationDTO(cr);
			carReservationsDTO.add(crdto);
		}
		return carReservationsDTO;
	}

	
	
	public List<FlightReservationDTO> getFlightReservations(String email) {
		User u = repository.findByEmail(email);
		Collection<FlightReservation> flightReservations  = flightReservationRepository.findFlightReservationsOfUser(u.getId());
		
		List<FlightReservationDTO> flightReservationsDTO = new ArrayList<>();
		for (FlightReservation fr : flightReservations) {
			FlightReservationDTO frdto = new FlightReservationDTO(fr);
			flightReservationsDTO.add(frdto);
		}
		return flightReservationsDTO;
	}
	
	public List<RoomReservationDTO> getRoomReservations(String email) {
		User u = repository.findByEmail(email);
		Collection<RoomReservation> roomReservations = roomReservationRepository.findRoomReservationsOfUser(u.getId());
		List<RoomReservationDTO> roomReservationsDTO = new ArrayList<>();
		for (RoomReservation rr : roomReservations) {
			RoomReservationDTO rrdto = new RoomReservationDTO(rr);
			roomReservationsDTO.add(rrdto);
		}
		return roomReservationsDTO;	
	}
	
	
	
	public List<FriendRequestDTO> getSentFriendRequests(String email) {
		User u = repository.findByEmail(email);
		List<FriendRequestDTO> reqs = new ArrayList<>();
		if (u != null && u.isEnabled()) {
			u.getSentRequests().size();
			for (FriendRequest fr : u.getSentRequests()) 
				reqs.add(new FriendRequestDTO(fr));
		}
		return reqs;
	}
	
	
	public List<FriendRequestDTO> getRecievedFriendRequests(String email) {
		User u = repository.findByEmail(email);
		List<FriendRequestDTO> reqs = new ArrayList<>();
		if (u != null && u.isEnabled()) {
			u.getRecievedRequests().size();
			for (FriendRequest fr : u.getRecievedRequests()) 
				reqs.add(new FriendRequestDTO(fr));
		}
		return reqs;
	}
	
	
	

	public boolean sendFriendRequest(String sender, String reciever) {
		if (verifyFriendRequest(sender, reciever)) {
			User s = repository.findByEmail(sender);
			User r = repository.findByEmail(reciever);
			FriendRequest fr = new FriendRequest(s, r, new Date());
			s.getSentRequests().add(fr);
			r.getRecievedRequests().add(fr);
			friendRequestsRepository.save(fr);
			repository.save(s);
			repository.save(r);
			return true;
		}
		return false;
	}
	
	
	
	public boolean approveFriendRequest(String sender, String reciever) {
		if (verifyApproveRequest(sender, reciever)) {
			User r = repository.findByEmail(reciever);
			User s = repository.findByEmail(sender);
			FriendRequest fr = friendRequestsRepository.findFriendRequest(s.getId(), r.getId());
			r.getFriends().add(s);
			s.getFriends().add(r);
			repository.save(s);
			repository.save(r);
			friendRequestsRepository.delete(fr);
			return true;
		}
		return false;
	}
	
	
	
	public boolean declineFriendRequest(String sender, String reciever) {
		if(verifyDeclineRequest(sender, reciever)) {
			User r = repository.findByEmail(reciever);
			User s = repository.findByEmail(sender);
			FriendRequest fr = friendRequestsRepository.findFriendRequest(s.getId(), r.getId());
			friendRequestsRepository.delete(fr);
			return true;
		}
		return false;
	}
	
	
	
	
	public boolean removeFriend(String user, String userToRemove) {
		if (verifyRemoveFriend(user, userToRemove)) {
			User s = repository.findByEmail(user);
			User r = repository.findByEmail(userToRemove);
			s.getFriends().remove(r);
			r.getFriends().remove(s);
			repository.save(s);
			repository.save(r);
			return true;
		}
		return false;
	}
	
	
	
	
	private boolean verifyFriendRequest(String sender, String reciever) {
		// not adding myself
		if (sender.equals(reciever))
			return false;
		
		// both users exists
		User s = repository.findByEmail(sender);
		User r = repository.findByEmail(reciever);
		if (s == null || r == null)
			return false;
		
		if (!s.isEnabled() || !r.isEnabled())
			return false;
		
		// friend request exists
		if (friendRequestsRepository.findFriendRequest(s.getId(), r.getId()) != null)
			return false;
		
		// not friends
		if (repository.areFriends(s.getId(), r.getId()) > 0)
			return false;
		
		return true;
	}
	
	
	
	private boolean verifyApproveRequest(String sender, String reciever) {
		if (sender.equals(reciever))
			return false;
		
		// both users exists
		User s = repository.findByEmail(sender);
		User r = repository.findByEmail(reciever);
		if (s == null || r == null)
			return false;
		
		if (!s.isEnabled() || !r.isEnabled())
			return false;
		
		// request exists
		FriendRequest fr = friendRequestsRepository.findFriendRequest(s.getId(), r.getId());
		if (fr == null)
			return false;
		
		// sender and reciever must be in correct order
		// (sender cannot approve his own request)
		if (!fr.getSender().getEmail().equals(sender) || !fr.getReciever().getEmail().equals(reciever))
			return false;
		
		return true;
	}
	
	
	
	private boolean verifyDeclineRequest(String sender, String reciever) {
		if (sender.equals(reciever))
			return false;
		
		// both users exists
		User s = repository.findByEmail(sender);
		User r = repository.findByEmail(reciever);
		if (s == null || r == null)
			return false;
		
		if (!s.isEnabled() || !r.isEnabled())
			return false;
		
		// request exists
		FriendRequest fr = friendRequestsRepository.findFriendRequest(s.getId(), r.getId());
		if (fr == null)
			return false;
		
		return true;
	}
	
	
	
	private boolean verifyRemoveFriend(String sender, String reciever) {
		// not removing myself
		if (sender.equals(reciever))
			return false;
		
		// both users exists
		User s = repository.findByEmail(sender);
		User r = repository.findByEmail(reciever);
		if (s == null || r == null)
			return false;
		
		
		// both users are enabled
		if (!s.isEnabled() || !r.isEnabled())
			return false;
		
		
		// must be friends
		if (repository.areFriends(s.getId(), r.getId()) == 0)
			return false;
		
		return true;
	}
	
}
