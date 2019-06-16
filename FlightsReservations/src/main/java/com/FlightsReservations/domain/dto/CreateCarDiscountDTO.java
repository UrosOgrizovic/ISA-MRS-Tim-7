package com.FlightsReservations.domain.dto;

import java.util.Date;

import javax.validation.constraints.Future;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CreateCarDiscountDTO {
	private Long carId;
	
	@Future
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone="Europe/Belgrade")
	private Date startTime;
	
	@Future
	@JsonFormat(pattern = "dd-MM-yyyy HH:mm", timezone="Europe/Belgrade")
	private Date endTime;
	
	public CreateCarDiscountDTO() {
		super();
	}
	private float discountValue;
	
	public Long getCarId() {
		return carId;
	}
	public void setCarId(Long carId) {
		this.carId = carId;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public float getDiscountValue() {
		return discountValue;
	}
	public void setDiscountValue(float discountValue) {
		this.discountValue = discountValue;
	}
	public CreateCarDiscountDTO(Long carId, Date startTime, Date endTime, float discountValue) {
		super();
		this.carId = carId;
		this.startTime = startTime;
		this.endTime = endTime;
		this.discountValue = discountValue;
	}
	
}