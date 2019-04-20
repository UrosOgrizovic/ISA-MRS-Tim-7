package com.FlightsReservations.domain;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User extends AbstractUser implements UserDetails {

	// friends attribute will be added

	private static final long serialVersionUID = 1L;

	private boolean enabled;

	/*
	 * so as to only allow refreshing a token that was created before the latest
	 * password reset
	 */

	private Date lastPasswordResetDate;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "user_authority", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "authority_id", referencedColumnName = "id"))
	private List<Authority> authorities;
	
	
	
	@OneToMany(mappedBy = "owner")
	private Set<FlightReservation> flightReservations = new HashSet<>();
	
	//@Column(nullable = false)
	//private Integer flightBonusPoints;

	public User() {
		super();
	}

	public User(String firstName, String lastName, String email, String phone, String address, String password,
			boolean enabled, Integer flightBonusPoints) {
		super(firstName, lastName, email, phone, address, password);
		//this.flightBonusPoints = flightBonusPoints;
		
		this.enabled = enabled;
		
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getUsername() {
		return this.getEmail();
	}

	public Date getLastPasswordResetDate() {
		return lastPasswordResetDate;
	}

	public void setLastPasswordResetDate(Date lastPasswordResetDate) {
		this.lastPasswordResetDate = lastPasswordResetDate;
	}

	public Set<FlightReservation> getReservations() {
		return flightReservations;
	}

	public void setReservations(Set<FlightReservation> airReservations) {
		this.flightReservations = airReservations;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.enabled;
	}
}
