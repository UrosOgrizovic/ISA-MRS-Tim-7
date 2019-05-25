package com.FlightsReservations.domain;


import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
@DiscriminatorValue("A")
public class Admin extends AbstractUser 
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String firstName;
	
	@Column(nullable = false)
	private String lastName;
	
	@Column(nullable = false)
	private String email; // unique
	
	@Column
	private String phone;
	
	@Column
	private String address;
	
	@Column(nullable = false)
	private String password;
	
	@Column
	private String picturePath;
	
	@Column
	private AdminType type;
	
	public Admin() {
	}
	
	public Admin(String firstName, String lastName, String email, String phone, String address, String password,
			String picturePath, String type) 
	{
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.address = address;
		this.password = password;
		this.picturePath = picturePath;
		this.type = AdminType.valueOf(type);
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

	public AdminType getType()
	{
		return type;
	}

	public void setType(AdminType type)
	{
		this.type = type;
	}

}
