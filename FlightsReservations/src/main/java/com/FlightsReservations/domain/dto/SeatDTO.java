package com.FlightsReservations.domain.dto;

import com.FlightsReservations.domain.Seat;

public class SeatDTO {
	private Integer seatNum;
	private Boolean available;
	private String typeClass;
	private Long flightId;
	private boolean quickAvailable;

	public SeatDTO() {
		super();
	}

	public SeatDTO(Integer seatNum, boolean available, String typeClass, Long flightId, boolean quickAvailable) {
		super();
		this.seatNum = seatNum;
		this.available = available;
		this.typeClass = typeClass;
		this.flightId = flightId;
		this.quickAvailable = quickAvailable;
	}

	public SeatDTO(Seat s) {
		this(s.getSeatNumber(), s.isAvailable(), s.getType().toString(), s.getFlight().getId(), s.isQuickAvailable());
	}

	public Integer getSeatNum() {
		return seatNum;
	}

	public void setSeatNum(Integer seatNum) {
		this.seatNum = seatNum;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getTypeClass() {
		return typeClass;
	}

	public void setTypeClass(String typeClass) {
		this.typeClass = typeClass;
	}

	public Long getFlightId() {
		return flightId;
	}

	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public boolean isQuickAvailable() {
		return quickAvailable;
	}

	public void setQuickAvailable(boolean quickAvailable) {
		this.quickAvailable = quickAvailable;
	}

}
