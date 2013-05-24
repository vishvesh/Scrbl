package com.scrbl.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.transaction.annotation.Transactional;

import com.scrbl.dao.UsersDao;
import com.scrbl.model.Users;

public class UsersDaoImpl extends AbstractDao implements UsersDao {

	Logger logger = Logger.getLogger(getClass());
	
    @Transactional
    @Override
    public Users getUserByEmail(String email) {
    	logger.info("Email inside User Dao ::::" + email);
        return (Users) sessionFactory.getCurrentSession().
                createCriteria(Users.class).
                add(Restrictions.eq("email", email)).uniqueResult();
    }

    @Transactional
	@Override
	public Users saveOrUpdate(Users user) {
		sessionFactory.getCurrentSession().saveOrUpdate(user);
        return user;
	}

    @Transactional
	@Override
	public void delete(Users user) {
		sessionFactory.getCurrentSession().delete(user);		
	}

    @Transactional
	@Override
	public Users getById(Long Id) {
		return (Users) sessionFactory.getCurrentSession().get(Users.class, Id);
	}
    
    @Transactional
    @Override
    public Users getById(Integer Id) {
    	return (Users) sessionFactory.getCurrentSession().get(Users.class, Id);
    }

	@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Users> getAll() {
    	return sessionFactory.getCurrentSession().createCriteria(Users.class).list(); 
	}

	/*@SuppressWarnings("unchecked")
	@Transactional
	@Override
	public List<Users> searchUser(String loggedInUser, String namesToSearch) {
		return sessionFactory.getCurrentSession().
                createCriteria(Users.class).
                add(Restrictions.
                		or(Restrictions.like("firstName", '%'+namesToSearch+'%'), 
                				Restrictions.like("lastName", '%'+namesToSearch+'%')
                				)
                				).
                add(Restrictions.ne("userEmail", loggedInUser))
                .list();
	}*/
}