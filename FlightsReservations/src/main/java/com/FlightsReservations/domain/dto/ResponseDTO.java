package com.FlightsReservations.domain.dto;

public class ResponseDTO<T> {
	private T body;
	private String message;

	public ResponseDTO() {
		super();
	}

	public ResponseDTO(T body) {
		super();
		this.body = body;
	}

	public ResponseDTO(String message) {
		super();
		this.message = message;
	}

	public ResponseDTO(T body, String message) {
		super();
		this.body = body;
		this.message = message;
	}

	public T getBody() {
		return body;
	}

	public void setBody(T body) {
		this.body = body;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
