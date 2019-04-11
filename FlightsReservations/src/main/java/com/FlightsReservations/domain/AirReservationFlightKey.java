package com.FlightsReservations.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AirReservationFlightKey implements Serializable {

	private static final long serialVersionUID = -3095374680749671532L;

	@Column(name = "reservation_id")
	private Long reservationId;

	@Column(name = "flight_id")
	private Long flightId;

	public AirReservationFlightKey() {
		super();
	}

	public AirReservationFlightKey(Long reservationId, Long flightId) {
		super();
		this.reservationId = reservationId;
		this.flightId = flightId;
	}
	
	public Long getReservationId() {
		return reservationId;
	}

	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((flightId == null) ? 0 : flightId.hashCode());
		result = prime * result + ((reservationId == null) ? 0 : reservationId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AirReservationFlightKey other = (AirReservationFlightKey) obj;
		if (flightId == null) {
			if (other.flightId != null)
				return false;
		} else if (!flightId.equals(other.flightId))
			return false;
		if (reservationId == null) {
			if (other.reservationId != null)
				return false;
		} else if (!reservationId.equals(other.reservationId))
			return false;
		return true;
	}
}
