package com.FlightsReservations.service;

import java.util.Collection;

public interface IService<Type,Key> {
	Type create(Type t);
	Type update(Type t);
	Type findOne(Key id);
	void delete(Key id);
	Collection<Type> findAll();
}
