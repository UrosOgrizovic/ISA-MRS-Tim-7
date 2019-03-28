package com.FlightsReservations.repository;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.RACS;

@Repository
public class InMemoryRACSRepository {
	private AtomicLong counter = new AtomicLong(); // thread safe while Long is not
	private ConcurrentHashMap<Long, RACS> racss = new ConcurrentHashMap<Long, RACS>();

	@PostConstruct
	private void init_racs() {
		// Entry is an interface and cannot be instantiated. Hence, SimpleEntry is used.
		SimpleEntry<String, Double> plItem1 = new SimpleEntry("service1", 100);
		SimpleEntry<String, Double> plItem2 = new SimpleEntry("service2", 200);
		SimpleEntry<String, Double> plItem3 = new SimpleEntry("service3", 300);
		SimpleEntry<String, Double> plItem4 = new SimpleEntry("service4", 400);
		ArrayList<Entry<String, Double>> pricelist1 = new ArrayList<Entry<String, Double>>();
		pricelist1.add(plItem1);
		pricelist1.add(plItem2);
		ArrayList<Entry<String, Double>> pricelist2 = new ArrayList<Entry<String, Double>>();
		pricelist2.add(plItem3);
		pricelist2.add(plItem4);
		
		Car c1 = new Car("blue", "honda", "civic", 2010);
		Car c2 = new Car("red", "ferrari", "spider", 2008);
		Car c3 = new Car("white", "toyota", "supra", 2001);
		Car c4 = new Car("yellow", "fiat", "501", 1960);
		ArrayList<Car> cars1 = new ArrayList<Car>(); 
		cars1.add(c1); 
		cars1.add(c2);
		ArrayList<Car> cars2 = new ArrayList<Car>();
		cars2.add(c3);
		cars2.add(c4);
		String bo1 = "bo1";
		String bo2 = "bo2";
		String bo3 = "bo3";
		String bo4 = "bo4";
		ArrayList<String> branchOffices1 = new ArrayList<String>();
		branchOffices1.add(bo1);
		branchOffices1.add(bo2);
		ArrayList<String> branchOffices2 = new ArrayList<String>();
		branchOffices2.add(bo3);
		branchOffices2.add(bo4);
		RACS r1 = new RACS("r1", "adr1", "desc1", pricelist1, cars1, branchOffices1);
		RACS r2 = new RACS("r2", "adr2", "desc2", pricelist2, cars2, branchOffices2);
		create(r1);
		create(r2);
		System.out.println("RACS created!");
	}
	
	public RACS create(RACS r) {
		Long id = counter.getAndIncrement();
		r.setId(id);
		racss.put(id, r);
		return r;
	}

	public RACS update(RACS r) {
		racss.put(r.getId(), r);
		return r;
	}

	public RACS findOne(Long id) {
		return racss.get(id);
	}

	public void delete(Long id) {
		// TODO Auto-generated method stub
	}

	public Collection<RACS> findAll() {
		return racss.values();
	}

}
