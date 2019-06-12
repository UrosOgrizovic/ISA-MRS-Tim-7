package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
		AbstractUser u = abstractUserRepository.findByEmail(t.getEmail());
		if (u != null) {
			u.setFirstName(t.getFirstName());
			u.setLastName(t.getLastName());
			u.setPhone(t.getPhone());
			u.setAddress(t.getAddress());
			u.setEnabled(t.isEnabled());
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
	
	
	
	public List<AbstractUserDTO> getFriends(String email) {
		
		AbstractUser abstractUser = abstractUserRepository.findByEmail(email);
		
		ArrayList<String> types = new ArrayList<String>();
		abstractUser.getAuthorities().forEach(auth -> types.add(auth.getAuthority()));
		
		if (types.contains("A")) return null;
		User u = (User) abstractUser;
		List<User> friends = userRepository.findFriends(u.getId());
		List<AbstractUserDTO> friendsDTO = new ArrayList<>();
		for (User user : friends) {
			if (user.isEnabled()) {
				AbstractUserDTO udto = new AbstractUserDTO(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPhone(), user.getAddress(), true);
				friendsDTO.add(udto);
			}
		}
		return friendsDTO;
	}
	
	
	
	public void addFriend(Long userId, Long friendId) {
		userRepository.addFriend(userId, friendId);
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
	
	
	
	public List<FriendRequestDTO> getSentFriendRequests(String email) {
		User u = userRepository.findByEmail(email);
		List<FriendRequestDTO> reqs = new ArrayList<>();
		if (u != null && u.isEnabled()) {
			u.getSentRequests().size();
			for (FriendRequest fr : u.getSentRequests()) 
				reqs.add(new FriendRequestDTO(fr));
		}
		return reqs;
	}
	
	
	public List<FriendRequestDTO> getRecievedFriendRequests(String email) {
		User u = userRepository.findByEmail(email);
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
			User s = userRepository.findByEmail(sender);
			User r = userRepository.findByEmail(reciever);
			FriendRequest fr = new FriendRequest(s, r, new Date());
			s.getSentRequests().add(fr);
			r.getRecievedRequests().add(fr);
			friendRequestsRepository.save(fr);
			abstractUserRepository.save(s);
			abstractUserRepository.save(r);
			return true;
		}
		return false;
	}
	
	
	
	public boolean approveFriendRequest(String sender, String reciever) {
		if (verifyApproveRequest(sender, reciever)) {
			User r = userRepository.findByEmail(reciever);
			User s = userRepository.findByEmail(sender);
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
	
	
	
	public boolean declineFriendRequest(String sender, String reciever) {
		if(verifyDeclineRequest(sender, reciever)) {
			User r = userRepository.findByEmail(reciever);
			User s = userRepository.findByEmail(sender);
			FriendRequest fr = friendRequestsRepository.findFriendRequest(s.getId(), r.getId());
			friendRequestsRepository.delete(fr);
			return true;
		}
		return false;
	}
	
	
	
	
	public boolean removeFriend(String user, String userToRemove) {
		if (verifyRemoveFriend(user, userToRemove)) {
			User s = userRepository.findByEmail(user);
			User r = userRepository.findByEmail(userToRemove);
			s.getFriends().remove(r);
			r.getFriends().remove(s);
			abstractUserRepository.save(s);
			abstractUserRepository.save(r);
			return true;
		}
		return false;
	}
	
	
	
	
	private boolean verifyFriendRequest(String sender, String reciever) {
		// not adding myself
		if (sender.equals(reciever))
			return false;
		
		// both users exists
		User s = userRepository.findByEmail(sender);
		User r = userRepository.findByEmail(reciever);
		if (s == null || r == null)
			return false;
		
		if (!s.isEnabled() || !r.isEnabled())
			return false;
		
		// friend request exists
		if (friendRequestsRepository.findFriendRequest(s.getId(), r.getId()) != null)
			return false;
		
		// not friends already
		if (userRepository.areFriends(s.getId(), r.getId()) > 0)
			return false;
		
		return true;
	}
	
	
	
	private boolean verifyApproveRequest(String sender, String reciever) {
		if (sender.equals(reciever))
			return false;
		
		// both users exists
		User s = userRepository.findByEmail(sender);
		User r = userRepository.findByEmail(reciever);
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
		User s = userRepository.findByEmail(sender);
		User r = userRepository.findByEmail(reciever);
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
		User s = userRepository.findByEmail(sender);
		User r = userRepository.findByEmail(reciever);
		if (s == null || r == null)
			return false;
		
		
		// both users are enabled
		if (!s.isEnabled() || !r.isEnabled())
			return false;
		
		
		// must be friends
		if (userRepository.areFriends(s.getId(), r.getId()) == 0)
			return false;
		
		return true;
	}
	
}
