package com.FlightsReservations.domain.dto;

public class RoomDTO {

	private float averageScore;

<<<<<<< HEAD
=======
public class RoomDTO {
	@NotNull
	private float averageScore;
	@NotNull
>>>>>>> master
	private double overNightStay;
	
	private int number;
	private int numberOfGuests;	
	private int floor;
	private String type;
	
	private String hotelName;
	
	private int numberOfVotes;
<<<<<<< HEAD
		
=======
	
>>>>>>> master
	public int getNumberOfVotes() {
		return numberOfVotes;
	}
	public void setNumberOfVotes(int numberOfVotes) {
		this.numberOfVotes = numberOfVotes;
	}
	public void setAverageScore(float averageScore) {
		this.averageScore = averageScore;
	}
	public float getAverageScore() 
	{
		return averageScore;
	}
	
	public double getOverNightStay() 
	{
		return overNightStay;
	}
	public void setOverNightStay(double overNightStay) 
	{
		this.overNightStay = overNightStay;
	}
<<<<<<< HEAD
=======
	
	public Hotel getHotel() {
		return hotel;
	}
	public void setHotel(Hotel hotel) {
		this.hotel = hotel;
	}
>>>>>>> master
	public int getNumber()
	{
		return number;
	}
	public void setNumber(int number)
	{
		this.number = number;
	}
	public int getNumberOfGuests()
	{
		return numberOfGuests;
	}
	public void setNumberOfGuests(int numberOfGuests)
	{
		this.numberOfGuests = numberOfGuests;
	}
	public String getType()
	{
		return type;
	}
	public void setType(String type)
	{
		this.type = type;
	}
	public RoomDTO() 
	{
		super();
	}
<<<<<<< HEAD
	public String getHotelName()
	{
		return hotelName;
	}
	public void setHotelName(String hotelName)
	{
		this.hotelName = hotelName;
	}
	public int getFloor()
	{
		return floor;
	}
	public void setFloor(int floor)
=======
	
	public RoomDTO(@NotNull float averageScore, @NotNull double overNightStay, @NotNull int number,
			@NotNull int numberOfGuests, @NotBlank String name, @NotBlank String type, Hotel hotel, int numberOfVotes) {
		super();
		this.averageScore = averageScore;
		this.overNightStay = overNightStay;
		this.number = number;
		this.numberOfGuests = numberOfGuests;
		this.name = name;
		this.type = type;
		this.hotel = hotel;
		this.numberOfVotes = numberOfVotes;
	}
	public String getName()
>>>>>>> master
	{
		this.floor = floor;
	}
	public RoomDTO(int number, int floor, int numberOfGuests, String type, float averageScore, double overNightStay, String hotelName, int numberOfVotes)
	{
		super();
		this.averageScore = averageScore;
		this.overNightStay = overNightStay;
		this.number = number;
		this.floor = floor;
		this.numberOfGuests = numberOfGuests;
		this.type = type;
		this.hotelName = hotelName;
		this.numberOfVotes = numberOfVotes;
	}
	
	
}