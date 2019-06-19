package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.AbstractUser;
import com.FlightsReservations.domain.Authority;
import com.FlightsReservations.domain.CarReservation;
import com.FlightsReservations.domain.FlightReservation;
import com.FlightsReservations.domain.FriendRequest;
import com.FlightsReservations.domain.RoomReservation;
import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.AbstractUserDTO;
import com.FlightsReservations.domain.dto.CarReservationDTO;
import com.FlightsReservations.domain.dto.FlightReservationDTO;
import com.FlightsReservations.domain.dto.FriendRequestDTO;
import com.FlightsReservations.domain.dto.RegistrationUserDTO;
import com.FlightsReservations.domain.dto.RoomReservationDTO;
import com.FlightsReservations.repository.AbstractUserRepository;
import com.FlightsReservations.repository.AuthorityRepository;
import com.FlightsReservations.repository.CarReservationRepository;
import com.FlightsReservations.repository.FlightReservationRepository;
import com.FlightsReservations.repository.FriendRequestRepository;
import com.FlightsReservations.repository.RoomReservationRepository;
import com.FlightsReservations.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private AbstractUserRepository abstractUserRepository;
	
	@Autowired
	private UserRepository userRepository;
	
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
	
	
	public AbstractUserDTO myDetails() {
		AbstractUser u = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		u = abstractUserRepository.findByEmail(u.getEmail());
		return new AbstractUserDTO(u);
	}
	
	
	public AbstractUserDTO create(RegistrationUserDTO t) {
		if (abstractUserRepository.findByEmail(t.getEmail()) == null) {
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
			abstractUserRepository.save(u);
			return new AbstractUserDTO(u);
		}
		return null;
	}

	
	public boolean update(AbstractUserDTO t) {
		AbstractUser u = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		u = abstractUserRepository.findByEmail(u.getEmail());
		if (u != null) {
			u.setFirstName(t.getFirstName());
			u.setLastName(t.getLastName());
			u.setPhone(t.getPhone());
			u.setAddress(t.getAddress());
			abstractUserRepository.save(u);
			return true;
		}
		return false;
	}

	
	public AbstractUserDTO findOne(String email) {
		AbstractUser u = abstractUserRepository.findByEmail(email);
		if (u != null)
			return new AbstractUserDTO(u);
		return null;
	}
	
	
	public Optional<AbstractUser> findById(Long id) {
		return abstractUserRepository.findById(id);
	}
	
	
	public List<AbstractUserDTO> findAll() {
		List<AbstractUserDTO> dtos = new ArrayList<>();
		for (AbstractUser u : abstractUserRepository.findAll())
			dtos.add(new AbstractUserDTO(u));
		return dtos;
	}

	
	public List<CarReservationDTO> getCarReservations(String email) {
		AbstractUser u = abstractUserRepository.findByEmail(email);
		
		ArrayList<String> types = new ArrayList<String>();
		
		u.getAuthorities().forEach(auth -> types.add(auth.getAuthority()));
		for (int i = 0; i < types.size(); i++) {
			if (types.get(i).contains("USER")) types.set(i, "U");
			else if (types.get(i).contains("ADMIN")) types.set(i, "A");
			
		}
		
		Collection<CarReservation> carReservations = carReservationRepository.findCarReservationsOfUser(u.getId(), types.get(types.size()-1));
		List<CarReservationDTO> carReservationsDTO = new ArrayList<>();
		for (CarReservation cr : carReservations) {
			CarReservationDTO crdto = new CarReservationDTO(cr);
			carReservationsDTO.add(crdto);
		}
		return carReservationsDTO;
	}

	
	
	public List<FlightReservationDTO> getFlightReservations(String email) {
		AbstractUser u = abstractUserRepository.findByEmail(email);
		Collection<FlightReservation> flightReservations  = flightReservationRepository.findFlightReservationsOfUser(u.getId());
		
		List<FlightReservationDTO> flightReservationsDTO = new ArrayList<>();
		for (FlightReservation fr : flightReservations) {
			FlightReservationDTO frdto = new FlightReservationDTO(fr);
			flightReservationsDTO.add(frdto);
		}
		return flightReservationsDTO;
	}
	
	
	public List<RoomReservationDTO> getRoomReservations(String email) {
		AbstractUser u = abstractUserRepository.findByEmail(email);
		Collection<RoomReservation> roomReservations = roomReservationRepository.findRoomReservationsOfUser(u.getId());
		List<RoomReservationDTO> roomReservationsDTO = new ArrayList<>();
		for (RoomReservation rr : roomReservations) {
			RoomReservationDTO rrdto = new RoomReservationDTO(rr);
			roomReservationsDTO.add(rrdto);
		}
		return roomReservationsDTO;	
	}
	
	
	
	public List<AbstractUserDTO> getFriends() {
		AbstractUser aUser = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userRepository.findByEmail(aUser.getEmail());
		List<AbstractUserDTO> friends = new ArrayList<>();
		for (User uu : u.getFriends())
			friends.add(new AbstractUserDTO(uu));
		return friends;
	}
	
	
	
	public List<FriendRequestDTO> getSentFriendRequests() {
		AbstractUser aUser = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userRepository.findByEmail(aUser.getEmail());
		List<FriendRequestDTO> reqs = new ArrayList<>();
		u.getSentRequests().size();
		for (FriendRequest fr : u.getSentRequests()) 
			reqs.add(new FriendRequestDTO(fr));
		return reqs;
	}
	
	
	public List<FriendRequestDTO> getRecievedFriendRequests() {
		AbstractUser aUser = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userRepository.findByEmail(aUser.getEmail());
		List<FriendRequestDTO> reqs = new ArrayList<>();
		u.getRecievedRequests().size();
		for (FriendRequest fr : u.getRecievedRequests()) 
			reqs.add(new FriendRequestDTO(fr));
		return reqs;
	}	
	

	public boolean sendFriendRequest(String recieverEmail) {
		AbstractUser aUser = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User sender = userRepository.findByEmail(aUser.getEmail());
		User reciever = userRepository.findByEmail(recieverEmail);
		 
		if (verifyFriendRequest(sender, reciever)) {
			FriendRequest fr = new FriendRequest(sender, reciever, new Date());
			sender.getSentRequests().add(fr);
			reciever.getRecievedRequests().add(fr);
			friendRequestsRepository.save(fr);
			abstractUserRepository.save(sender);
			abstractUserRepository.save(reciever);
			return true;
		}
		return false;
	}
	
	
	
	public boolean approveFriendRequest(String sender) {
		AbstractUser aUser = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User r = userRepository.findByEmail(aUser.getEmail());
		User s = userRepository.findByEmail(sender);
		 
		if (verifyApproveRequest(s, r)) {
			FriendRequest fr = friendRequestsRepository.findFriendRequest(s.getId(), r.getId());
			r.getFriends().add(s);
			s.getFriends().add(r);
			abstractUserRepository.save(s);
			abstractUserRepository.save(r);
			friendRequestsRepository.delete(fr);
			return true;
		}
		return false;
	}
	
	
	
	public boolean declineFriendRequest(String sender) {
		AbstractUser aUser = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User r = userRepository.findByEmail(aUser.getEmail());
		User s = userRepository.findByEmail(sender);
		
		if(verifyDeclineRequest(s, r)) {
			FriendRequest fr = friendRequestsRepository.findFriendRequest(s.getId(), r.getId());
			friendRequestsRepository.delete(fr);
			return true;
		}
		return false;
	}
	
	
	
	
	public boolean removeFriend(String userToRemove) {
		AbstractUser aUser = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userRepository.findByEmail(aUser.getEmail());
		User toRemove = userRepository.findByEmail(userToRemove);
		if (verifyRemoveFriend(u, toRemove)) {
			u.getFriends().remove(toRemove);
			toRemove.getFriends().remove(u);
			abstractUserRepository.save(u);
			abstractUserRepository.save(toRemove);
			return true;
		}
		return false;
	}
	
	
	
	
	private boolean verifyFriendRequest(User s, User r) {
		// reciever exists
		if (r == null)
			return false;
		
		// not adding myself
		if (s.getEmail().equals(r.getEmail()))
			return false;
		
		if (!r.isEnabled())
			return false;
		
		// friend request exists
		if (friendRequestsRepository.findFriendRequest(s.getId(), r.getId()) != null)
			return false;
		
		// not friends already
		if (userRepository.areFriends(s.getId(), r.getId()) > 0)
			return false;
		
		return true;
	}
	
	
	
	private boolean verifyApproveRequest(User s, User r) {
		// both users exists
		if (s == null)
			return false;
		
		if (s.getEmail().equals(r.getEmail()))
			return false;
		
		if (!s.isEnabled() || !r.isEnabled())
			return false;
		
		// request exists
		FriendRequest fr = friendRequestsRepository.findFriendRequest(s.getId(), r.getId());
		if (fr == null)
			return false;
		
		// sender and reciever must be in correct order
		// (sender cannot approve his own request)
		if (!fr.getSender().getEmail().equals(s.getEmail()) || !fr.getReciever().getEmail().equals(r.getEmail()))
			return false;
		
		return true;
	}
	
	
	
	private boolean verifyDeclineRequest(User s, User r) {
		// both users exists
		if (s == null || r == null)
			return false;
		
		if (s.getEmail().equals(r.getEmail()))
			return false;
		
		if (!s.isEnabled() || !r.isEnabled())
			return false;
		
		// request exists
		FriendRequest fr = friendRequestsRepository.findFriendRequest(s.getId(), r.getId());
		if (fr == null)
			return false;
		
		return true;
	}
	
	
	
	private boolean verifyRemoveFriend(User u, User toRemove) {
		// both users exists
		if (u == null || toRemove == null)
			return false;
		
		// not removing myself
		if (u.getEmail().equals(toRemove.getEmail()))
			return false;
		
		// must be friends
		if (userRepository.areFriends(u.getId(), toRemove.getId()) == 0)
			return false;
		
		return true;
	}


	public Collection<AbstractUserDTO> searchUsers(String query) {
		AbstractUser aUser = (AbstractUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		User u = userRepository.findByEmail(aUser.getEmail());
		query = query.toLowerCase();
		
		Set<AbstractUser> results = new HashSet<>();
		for (User uu : userRepository.findAll()) {
			if (query.contains(uu.getFirstName().toLowerCase()) || query.contains(uu.getLastName().toLowerCase())) {
				results.add(uu);
			}
		}
		
		for (User f : u.getFriends()) results.remove(f);
		for (FriendRequest rfr : u.getRecievedRequests()) results.remove(rfr.getSender());
		for (FriendRequest sfr : u.getSentRequests()) results.remove(sfr.getReciever());
		results.remove(u);
		
		Set<AbstractUserDTO> dtoResults = new HashSet<>();
		for (AbstractUser au : results)
			dtoResults.add(new AbstractUserDTO(au));
		return dtoResults;
	}


	
	
}
