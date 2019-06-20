package com.FlightsReservations;	

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableScheduling
public class FlightsReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightsReservationsApplication.class, args);
	}
}
