package com.FlightsReservations.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("U")
public class User extends AbstractUser {

	private static final long serialVersionUID = -4209208559406236164L;


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
		super(firstName, lastName, email, phone, address, password, enabled);
		//this.flightBonusPoints = flightBonusPoints;
	}

	

	@Override
	public String toString() {
		return "User [friends=" + friends + ", sentRequests=" + sentRequests + ", recievedRequests=" + recievedRequests
				+ ", friendOf=" + friendOf + ", flightReservations=" + flightReservations + ", getId()=" + getId()
				+ ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getEmail()="
				+ getEmail() + ", getPhone()=" + getPhone() + ", getAddress()=" + getAddress() + ", getPassword()="
				+ getPassword() + ", getAuthorities()=" + getAuthorities() + ", getUsername()=" + getUsername()
				+ ", isEnabled()=" + isEnabled() + "]";
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