/*package org.lazypizza.data.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.lazypizza.data.model.City;
import org.lazypizza.data.model.PizzaStores;
import org.lazypizza.data.model.StateProvince;
import org.lazypizza.data.model.StoreAddress;
import org.lazypizza.data.service.PizzaStoresService;
import org.springframework.transaction.annotation.Transactional;

public class PizzaStoresServiceImpl extends AbstractService implements PizzaStoresService {

	Logger logger = Logger.getLogger(getClass());

	
	@Transactional
	@Override
	public PizzaStores insertPizzaStores(PizzaStores pizzaStores) {
		return pizzaStoresDao.save(pizzaStores);
	}

	@Transactional
	@Override
	public List<PizzaStores> getAllPizzaStores() {
		return pizzaStoresDao.getAll();
	}

	@Transactional
	@Override
	public void deletePizzaStores(PizzaStores pizzaStores) {
		pizzaStoresDao.delete(pizzaStores);
		
	}

	@Transactional
	@Override
	public PizzaStores getById(Long storeId) {
		return pizzaStoresDao.getById(storeId);
	}
	
	@Transactional
	@Override
	public PizzaStores getPizzaStoreByLatLong(BigDecimal latitude, BigDecimal longitude) {
		logger.info("Came to pizza store by lag long");
		return pizzaStoresDao.getStoreByLatLong(latitude, longitude);
	}
	
	@Transactional
	@Override
	public PizzaStores getActivePizzaStoreByLatLong(BigDecimal latitude,
			BigDecimal longitude) {
		return pizzaStoresDao.getActiveStoreByLatLong(latitude, longitude);
	}
	
	@Transactional
	@Override
	public PizzaStores processAndSaveStoreFromRawData(String placeAddress,
			String storeName, String description, BigDecimal latitude, BigDecimal longitude) {
		
		String[] addressParts = placeAddress.split(",");
		String streetAddress = addressParts[0].trim();
		String cityString = addressParts[1].trim();
		String stateString = addressParts[2].trim();
		//TODO: allow adding of countries and states for countries, nothing immediate, but something to look at.
		//String countryString = addressParts[3].trim();
		
		City city = cityDao.getCityByCityAndState(cityString, stateString);
		if(city == null)
		{
			city = new City();
			StateProvince stateProvince = stateProvinceDao.getStateByStateAbbreviation(stateString);
			city.setCityName(cityString);
			city.setStateProvince(stateProvince);
			city = cityDao.save(city);
		}
		
		PizzaStores pizzaStores = new PizzaStores();
		pizzaStores.setStoreName(storeName);
		pizzaStores.setDescription(description);
		pizzaStores = pizzaStoresDao.save(pizzaStores);
		
		StoreAddress storeAddress = new StoreAddress();
		storeAddress.setId(pizzaStores.getId());
		storeAddress.setLatitude(latitude);
		storeAddress.setLongitude(longitude);
		storeAddress.setCity(city);
		storeAddress.setAddress(streetAddress);
		storeAddress.setPizzaStores(pizzaStores);
		
		storeAddress = storeAddressDao.save(storeAddress);
		
		pizzaStores.setStoreAddress(storeAddress);
		
		return pizzaStores;
	}
	
	@Transactional
	@Override
	public List<PizzaStores> getCurrentlyActivePizzaStores() {
		return pizzaStoresDao.getAllActivePizzaStores(new Date());
	}
	
	@Transactional
	@Override
	public PizzaStores getStoreInformationByWidgetId(String widgetId) {
		return pizzaStoresDao.getPizzaStoreByWidgetId(widgetId);
	}
}
*/