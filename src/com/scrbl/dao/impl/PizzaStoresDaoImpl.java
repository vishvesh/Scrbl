/*package org.lazypizza.data.dao.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.lazypizza.data.dao.PizzaStoresDao;
import org.lazypizza.data.model.PizzaStores;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

public class PizzaStoresDaoImpl extends AbstractDao implements PizzaStoresDao {

	Logger logger = Logger.getLogger(getClass());
	

    @Transactional
	@Override
	public PizzaStores save(PizzaStores institution) {
		sessionFactory.getCurrentSession().saveOrUpdate(institution);
		return institution;
	}

    @Transactional
	@Override
	public void delete(PizzaStores institution) {
    	sessionFactory.getCurrentSession().delete(institution);
	}

    @Transactional
	@Override
	public PizzaStores getById(Long Id) {
    	return (PizzaStores) sessionFactory.getCurrentSession().get(PizzaStores.class, Id);
	}
    
    @Transactional
   	@Override
   	public PizzaStores getById(Integer Id) {
       	return (PizzaStores) sessionFactory.getCurrentSession().get(PizzaStores.class, Id);
   	}

    @SuppressWarnings("unchecked")
    @Transactional
	@Override
	public List<PizzaStores> getAll() {
		return sessionFactory.getCurrentSession().createCriteria(PizzaStores.class).list();
	}

    @Transactional
    @Override
    public PizzaStores getStoreByLatLong(BigDecimal latitude, BigDecimal longitude) {
    	logger.info("long: " + longitude);
    	return (PizzaStores) sessionFactory.getCurrentSession()
    			.createCriteria(PizzaStores.class)
    			.createAlias("storeAddress", "sa")
    			.add(Restrictions.eq("sa.longitude", longitude))
    			.add(Restrictions.eq("sa.longitude", longitude))
    			.uniqueResult();
    }
    
    @Transactional
    @Override
    public PizzaStores getActiveStoreByLatLong(BigDecimal latitude,
    		BigDecimal longitude) {
    	return (PizzaStores) sessionFactory.getCurrentSession()
    			.createCriteria(PizzaStores.class)
    			.createAlias("storeAddress", "sa")
    			.add(Restrictions.isNotNull("activatedOn"))
    			.add(Restrictions.le("activatedOn", new Date()))
    			.add(Restrictions.eq("sa.longitude", longitude))
    			.add(Restrictions.eq("sa.longitude", longitude))
    			.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
    @Transactional
    @Override
    public List<PizzaStores> getAllActivePizzaStores(Date date) {
    	return sessionFactory.getCurrentSession()
    			.createCriteria(PizzaStores.class)
    			.add(Restrictions.le("activatedOn", date))
    			.list();
    }
    
    @Transactional
    @Override
    public PizzaStores getPizzaStoreByWidgetId(String widgetId) {
    	return (PizzaStores) sessionFactory.getCurrentSession()
    			.createCriteria(PizzaStores.class)
    			.add(Restrictions.eq("widgetId", widgetId))
    			.uniqueResult();
    }
}
*/