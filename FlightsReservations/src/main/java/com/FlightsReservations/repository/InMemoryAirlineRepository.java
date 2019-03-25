package com.FlightsReservations.repository;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.FlightsReservations.domain.Airline;

@Component
public class InMemoryAirlineRepository implements IRepository<Airline,Long>{

	private AtomicLong counter = new AtomicLong(); // thread safe while Long is not
	private ConcurrentHashMap<Long, Airline> airlines = new ConcurrentHashMap<Long, Airline>();
	
	@PostConstruct
	private void init_airlines() {
		Airline a1 = new Airline("AirSerbia", "Serbia", "Best in Serbia");
		Airline a2 = new Airline("AirRussia", "Russia", "Best in Russia");
		create(a1);
		create(a2);
		System.out.println("AIRLINES CREATED!");
	}
	
	@Override
	public Airline create(Airline t) {
		Long id = counter.getAndIncrement();
		t.setId(id);
		airlines.put(id, t);
		return t;
	}

	@Override
	public Airline update(Airline t) {
		airlines.put(t.getId(), t);
		return t;
	}

	@Override
	public Airline findOne(Long id) {
		return airlines.get(id);
	}

	@Override
	public Collection<Airline> findAll() {
		return airlines.values();
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

}
