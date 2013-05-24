package com.scrbl.service;

import java.util.List;

import org.apache.poi.ss.formula.functions.T;

/**
 * @author vdeshmukh
 * 
 * Keeping this interface for now but
 * not using it yet! Probably, will not use it
 * but will keep it anyways!
 */
public interface GenericService {
	
	public T saveOrUpdate(T object);
	public void delete(T object);
	public T getById(Long Id);
	public T getById(Integer Id);
	public List<T> getAll();
	
}
