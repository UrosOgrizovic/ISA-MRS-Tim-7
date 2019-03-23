package com.FlightsReservations.repository;

import java.util.Collection;

public interface IRepository<T> {
	T create(T t);
	T update(T t);
	T findOne(Long id);
	void delete(Long id);
	Collection<T> findAll();
}
