package com.FlightsReservations.common;

import java.util.Calendar;
import java.util.Date;

public class DateIterator {
	
	private Calendar start;
	private Calendar end;
	private Calendar counter;
	
	public DateIterator() {
		super();
	}
	
	public DateIterator(Date start, Date end) {
		this.start = Calendar.getInstance();
		this.start.setTime(start);
		this.end = Calendar.getInstance();
		this.end.setTime(end);
		this.counter = Calendar.getInstance();
		this.counter.setTime(start);
	}
	
	public DateIterator(Date end, int days) {
		this.end = Calendar.getInstance();
		this.end.setTime(end);
		this.start = Calendar.getInstance();
		this.start.setTime(end);
		this.start.add(Calendar.DATE, days);
		this.counter = Calendar.getInstance();
		this.counter.setTime(start.getTime());
	}
	
	public void increment() {
		counter.add(Calendar.DATE, 1);
	}
	
	public Date getCounter() {
		return counter.getTime().before(end.getTime()) ? counter.getTime() : null;
	}
	
}
