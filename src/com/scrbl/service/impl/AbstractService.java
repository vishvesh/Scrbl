package com.scrbl.service.impl;

import org.apache.log4j.Logger;

import com.scrbl.dao.UsersDao;

public abstract class AbstractService {
	
	Logger logger = Logger.getLogger(getClass());
	
	protected UsersDao userDao;	

	public void setUserDao(UsersDao userDao) {
		logger.info("Comes inside abs service");
		this.userDao = userDao;
	}

}
