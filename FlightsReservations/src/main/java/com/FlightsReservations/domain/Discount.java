package com.FlightsReservations.domain;

import java.util.Date;

import javax.persistence.Embeddable;


@Embeddable
public class Discount {
	private Date startTime;
	
	private Date endTime;
	private float discountValue;
	
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
	public Discount(Date startTime, Date endTime, float discountValue) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.discountValue = discountValue;
	}
	public Discount() {
		super();
	}
	
	
}