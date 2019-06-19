package com.FlightsReservations.domain;

public class UserTokenState {
	
    private String accessToken;
    private String email;
    private String firstName;
    private Long expiresIn;
    private String type;
    
    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
    }

    public UserTokenState(String accessToken, long expiresIn, String email, String firstName, String type) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.email = email;
        this.firstName = firstName;
        this.setType(type);
    }

    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}