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
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
			name="user_friends",
	 		joinColumns=@JoinColumn(name="user_id"),
	        inverseJoinColumns=@JoinColumn(name="friend_id"))
	private List<User> friends;


	@OneToMany(mappedBy = "sender", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FriendRequest> sentRequests;
	
	
	@OneToMany(mappedBy = "reciever", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<FriendRequest> recievedRequests;
	
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name="user_friends",
	 joinColumns=@JoinColumn(name="friend_id"),
	 inverseJoinColumns=@JoinColumn(name="user_id")
	)
	private List<User> friendOf;
	
	
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

	@Override
	public String toString() {
		return "User [getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getEmail()="
				+ getEmail() + "]";
	}

	public List<FriendRequest> getSentRequests() {
		return sentRequests;
	}

	public void setSentRequests(List<FriendRequest> sentRequests) {
		this.sentRequests = sentRequests;
	}

	public List<FriendRequest> getRecievedRequests() {
		return recievedRequests;
	}

	public void setRecievedRequests(List<FriendRequest> recievedRequests) {
		this.recievedRequests = recievedRequests;
	}

	public Set<FlightReservation> getFlightReservations() {
		return flightReservations;
	}

	public void setFlightReservations(Set<FlightReservation> flightReservations) {
		this.flightReservations = flightReservations;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}
	
	
	
}
