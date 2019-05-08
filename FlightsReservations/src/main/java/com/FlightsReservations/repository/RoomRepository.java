package com.FlightsReservations.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FlightsReservations.domain.Room;

public interface RoomRepository extends JpaRepository<Room, Long> {

}
