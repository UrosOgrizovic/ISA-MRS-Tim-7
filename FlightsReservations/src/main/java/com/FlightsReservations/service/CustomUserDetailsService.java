package com.FlightsReservations.service;

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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.FlightsReservations.domain.AbstractUser;
import com.FlightsReservations.repository.AbstractUserRepository;

@Service
@Transactional(propagation = Propagation.REQUIRED, readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private AbstractUserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AbstractUser user = repository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
		} else {
			return user;
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void changePassword(String oldPassword, String newPassword) {
		Authentication currentUser = SecurityContextHolder.getContext().getAuthentication();
		String username = currentUser.getName();

		if (authenticationManager != null) {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, oldPassword));
		} else {
			return;
		}

		AbstractUser user = (AbstractUser) loadUserByUsername(username);
		user.setPassword(passwordEncoder.encode(newPassword));
		repository.save(user);
	}

	public AbstractUserRepository getRepository() {
		return repository;
	}

	public void setRepository(AbstractUserRepository repository) {
		this.repository = repository;
	}

}
