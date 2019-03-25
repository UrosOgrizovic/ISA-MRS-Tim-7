package com.FlightsReservations.repository;

import java.util.Collection;

public interface IRepository<Type,Key> {
	Type create(Type t);
	Type update(Type t);
	Type findOne(Key id);
	void delete(Key id);
	Collection<Type> findAll();
}
