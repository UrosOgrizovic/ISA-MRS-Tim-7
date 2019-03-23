package com.FlightsReservations.service;

import java.util.Collection;

public interface IService<T> {
	T create(T t);
	T update(T t);
	T findOne(Long id);
	void delete(Long id);
	Collection<T> findAll();
}
