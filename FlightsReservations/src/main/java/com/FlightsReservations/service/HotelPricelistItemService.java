package com.FlightsReservations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FlightsReservations.domain.Hotel;
import com.FlightsReservations.domain.HotelPricelistItem;
import com.FlightsReservations.domain.dto.HotelPricelistItemDTO;
import com.FlightsReservations.repository.HotelPricelistItemRepository;
import com.FlightsReservations.repository.HotelRepository;

@Service
public class HotelPricelistItemService
{
	@Autowired
	HotelPricelistItemRepository repository;
	
	@Autowired
	HotelRepository hotelRepository;

	public HotelPricelistItemDTO create(HotelPricelistItemDTO dto)
	{
		HotelPricelistItem pli = repository.findByName(dto.getName() );
		if(pli==null)
		{
			Hotel hotel = hotelRepository.findByName(dto.getHotelName() );
			pli = new HotelPricelistItem(dto.getName(), dto.getPrice(), hotel);
			hotel.getPricelist().add(pli);
			repository.save(pli);
			hotelRepository.save(hotel);
			return createDTO(pli);
		}
		else return null;
	}

	private HotelPricelistItemDTO createDTO(HotelPricelistItem pli)
	{
		if(pli!=null)
		{
			HotelPricelistItemDTO dto;
			if(pli.getHotel()!=null) dto = new HotelPricelistItemDTO(pli.getName(), pli.getPrice(), pli.getHotel().getName() );
			else dto = new HotelPricelistItemDTO(pli.getName(), pli.getPrice(), "");
			return dto;
		}
		else return null;
	}
}
