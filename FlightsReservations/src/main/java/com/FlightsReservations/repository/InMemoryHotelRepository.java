package com.FlightsReservations.repository;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Room;
import com.FlightsReservations.domain.Hotel;

@Repository
public class InMemoryHotelRepository implements IRepository<Hotel, Long> {
	private AtomicLong counter = new AtomicLong(); // thread safe while Long is not
	private ConcurrentHashMap<Long, Hotel> hotels = new ConcurrentHashMap<Long, Hotel>();

	@PostConstruct
	private void init_Hotel() {
		// Entry is an interface and cannot be instantiated. Hence, SimpleEntry is used.
		
		SimpleEntry<String, Double> plItem1 = new SimpleEntry("hotel1", 100);
		SimpleEntry<String, Double> plItem2 = new SimpleEntry("hotel2", 200);
		SimpleEntry<String, Double> plItem3 = new SimpleEntry("hotel3", 300);
		SimpleEntry<String, Double> plItem4 = new SimpleEntry("hotel4", 400);
		ArrayList<Entry<String, Double>> pricelist1 = new ArrayList<Entry<String, Double>>();
		pricelist1.add(plItem1);
		pricelist1.add(plItem2);
		ArrayList<Entry<String, Double>> pricelist2 = new ArrayList<Entry<String, Double>>();
		pricelist2.add(plItem3);
		pricelist2.add(plItem4);
		
		Room r1 = new Room(2, "1 bedroom", "has only 1 bed");
		Room r2 = new Room(1, "1 bedroom", "has only 1 bed");
		Room r3 = new Room(2, "2 bedroom", "has 2 beds");
		Room r4 = new Room(2, "3 bedroom", "has 3 beds");
		ArrayList<Room> rooms1 = new ArrayList<Room>(); 
		rooms1.add(r1); 
		rooms1.add(r2);
		ArrayList<Room> rooms2 = new ArrayList<Room>();
		rooms2.add(r3);
		rooms2.add(r4);
		String bo1 = "bo1";
		String bo2 = "bo2";
		String bo3 = "bo3";
		String bo4 = "bo4";
		
		Hotel h1 = new Hotel("hotel1", "adr1", "desc1");
		Hotel h2 = new Hotel("hotel2", "adr2", "desc2");
		create(h1);
		create(h2);
		System.out.println("Hotels created!");
	}
	
	@Override
	public Hotel create(Hotel h) {
		Long id = counter.getAndIncrement();
		h.setId(id);
		hotels.put(id, h);
		return h;
	}

	@Override
	public Hotel update(Hotel h) {
		hotels.put(h.getId(), h);
		return h;
	}

	@Override
	public Hotel findOne(Long id) {
		return hotels.get(id);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
	}

	@Override
	public Collection<Hotel> findAll() {
		return hotels.values();
	}

}
