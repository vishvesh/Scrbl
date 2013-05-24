package com.scrbl.dao;

import java.util.List;

public interface GenericDao<T> {

	public T saveOrUpdate(T object);
	public void delete(T object);
	public T getById(Long Id);
	public T getById(Integer Id);
	public List<T> getAll();
	
}
