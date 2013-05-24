/*package org.lazypizza.data.dao;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.lazypizza.data.model.PizzaStores;

public interface PizzaStoresDao extends GenericDao<PizzaStores> {

	*//**
	 * 
	 * @param latitude
	 * @param longitude
	 * @return returns a pizza store based on the latlong values, null if none are found.
	 *//*
	public PizzaStores getStoreByLatLong(BigDecimal latitude, BigDecimal longitude);
	
	*//**
	 *  
	 * @param latitude
	 * @param longitude
	 * @return {@link PizzaStores} if an active store is found based on the latlong, null otherwise.
	 *//*
	public PizzaStores getActiveStoreByLatLong(BigDecimal latitude, BigDecimal longitude);
	
	*//**
	 * 
	 * 
	 * @param date specify the date at which the store was active, if today is the date then all stores active
	 * today will show up.
	 * @return
	 *//*
	public List<PizzaStores> getAllActivePizzaStores(Date date);
	
	*//**
	 * 
	 * @param widgetId
	 * @return
	 *//*
	public PizzaStores getPizzaStoreByWidgetId(String widgetId);
	
}
*/