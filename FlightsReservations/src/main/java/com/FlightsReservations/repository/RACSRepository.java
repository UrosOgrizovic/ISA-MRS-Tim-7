package com.FlightsReservations.repository;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FlightsReservations.domain.Airline;
import com.FlightsReservations.domain.Car;
import com.FlightsReservations.domain.RACS;

@Repository
public interface RACSRepository extends JpaRepository<RACS, Long> {
}
