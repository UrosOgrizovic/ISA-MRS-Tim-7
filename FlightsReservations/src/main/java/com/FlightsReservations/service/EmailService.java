package com.FlightsReservations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;
	
	@Async
	public void sendEmail(String emailTo, String subject, String text) {
		try {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(emailTo);
			mail.setFrom(env.getProperty("spring.mail.username"));
			mail.setSubject(subject);
			mail.setText(text);
			javaMailSender.send(mail);
		} catch (MailException e) {
			System.err.println("SENDING EMAIL FAILED.");
		}
	}
}
