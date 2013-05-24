package com.scrbl.dao.impl;

import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;

public abstract class AbstractDao {

	Logger logger = Logger.getLogger(getClass());
	
	protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
	
}
