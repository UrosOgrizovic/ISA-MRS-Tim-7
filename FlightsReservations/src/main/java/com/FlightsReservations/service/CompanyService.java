package com.FlightsReservations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Company;
import com.FlightsReservations.domain.dto.CompanyRatingDTO;
import com.FlightsReservations.repository.CompanyRepository;

@Service
public class CompanyService {
	
	@Autowired
	private CompanyRepository repository;
	
	public CompanyRatingDTO rate(CompanyRatingDTO dto) {
		Company c = repository.findByName(dto.getName());
		if (c != null) {
			float newAvgScore = c.getAverageScore() * c.getNumberOfVotes() + dto.getaverageScore();
			int newNumberOfVotes = c.getNumberOfVotes() + 1;
			c.setNumberOfVotes(newNumberOfVotes);
			c.setAverageScore(newAvgScore / newNumberOfVotes);
			repository.save(c);
			return new CompanyRatingDTO(c.getName(), c.getAverageScore());
		}		
		return null;
	}
}
