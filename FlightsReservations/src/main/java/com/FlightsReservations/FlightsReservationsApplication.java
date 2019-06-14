package com.FlightsReservations;	

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class FlightsReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(FlightsReservationsApplication.class, args);
	}
}
