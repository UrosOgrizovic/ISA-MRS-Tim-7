package com.FlightsReservations.domain;

public class Airline {
	private Long id;
	private String name;
	private String location;
	private String promoDescription;
	
	private AirlineAdmin airlineAdmin;
	private String logoPath;
	//TODO: add servicesPriceList, add overallRating, add additional services,...
	//TODO: add flights/destinations/planes collections
	// rest of attributes will be added later
	
	public Airline() {
		super();
	}
	
	public Airline(String name, String location, String promoDescription) {
		super();
		this.name = name;
		this.location = location;
		this.promoDescription = promoDescription;
	}
	
	public Airline(Long id, String name, String location, String promoDescription, String logoPath,
			AirlineAdmin airlineAdmin)
	{
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.promoDescription = promoDescription;
		this.logoPath = logoPath;
		this.airlineAdmin = airlineAdmin;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPromoDescription() {
		return promoDescription;
	}

	public void setPromoDescription(String promoDescription) {
		this.promoDescription = promoDescription;
	}

	public String getLogoPath()
	{
		return logoPath;
	}

	public void setLogoPath(String logoPath)
	{
		this.logoPath = logoPath;
	}

	public AirlineAdmin getAirlineAdmin()
	{
		return airlineAdmin;
	}

	public void setAirlineAdmin(AirlineAdmin airlineAdmin)
	{
		this.airlineAdmin = airlineAdmin;
	}
	
	
}
