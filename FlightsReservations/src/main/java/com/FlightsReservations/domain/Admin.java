package com.FlightsReservations.domain;

public class Admin
{
	private Long id;
	private String firstName;
	private String lastName;
	private String email; // unique
	private String phone;
	private String address;
	private String password;
	private String picturePath;
	
	public Admin() {
	}
	
	public Admin(Long id, String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath) 
	{
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.password = password;
		this.picturePath = picturePath;
	}
	
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getFirstName() 
	{
		return firstName;
	}
	
	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	
	public String getLastName() 
	{
		return lastName;
	}
	
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	
	public String getEmail() 
	{
		return email;
	}
	
	public void setEmail(String email) 
	{
		this.email = email;
	}
	
	public String getPhone() 
	{
		return phone;
	}
	
	public void setPhone(String phone) 
	{
		this.phone = phone;
	}
	
	public String getAddress() 
	{
		return address;
	}
	
	public void setAddress(String address) 
	{
		this.address = address;
	}
	
	//TODO: edit later@JsonIgnore
	public String getPassword() 
	{
		return password;
	}
	
	public void setPassword(String password) 
	{
		this.password = password;
	}
	
	public String getPicturePath() 
	{
		return picturePath;
	}
	
	public void setPicturePath(String picturePath) 
	{
		this.picturePath = picturePath;
	}

}
