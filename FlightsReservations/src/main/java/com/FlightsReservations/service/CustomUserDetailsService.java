package com.FlightsReservations.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.User;
import com.FlightsReservations.domain.dto.RegistrationUserDTO;
import com.FlightsReservations.domain.dto.UserDTO;
import com.FlightsReservations.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

	protected final Log LOGGER = LogFactory.getLog(getClass());

	@Autowired
	private UserRepository repository;
	
	public UserRepository getRepository() {
		return repository;
	}

	public void setRepository(UserRepository repository) {
		this.repository = repository;
	}

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		User user = repository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
		} else {
			return user;
		}
	}
	
	// This function allows the user to change their password
	public void changePassword(String oldPassword, String newPassword) {

		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			LOGGER.debug("Re-authenticating user '" + username + "' for password change request.");

			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			LOGGER.debug("No authentication manager set. Can't change Password!");

			return;
		}

		LOGGER.debug("Changing password for user '" + username + "'");

		User user = (User) loadUserByUsername(username);

		// hash password before inserting into db
		user.setPassword(passwordEncoder.encode(newPassword));
		repository.save(user);
	}

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
	
	public List<RegistrationUserDTO> findAllRegistrationUsers() {
		List<RegistrationUserDTO> rdtos = new ArrayList<>();
		for (User u : repository.findAll()) {
			rdtos.add(new RegistrationUserDTO(u));
		}
		return rdtos;
	}
}
