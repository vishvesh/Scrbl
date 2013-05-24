/*package org.lazypizza.data.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.FetchMode;
import org.hibernate.criterion.Restrictions;
import org.lazypizza.data.dao.UsersDao;
import org.lazypizza.data.model.Users;
import org.springframework.transaction.annotation.Transactional;

public class UsersDaoImpl extends AbstractDao implements UsersDao {

	Logger logger = Logger.getLogger(getClass());
	

    @Transactional
    @Override
    public Users getUserByUserName(String userName) {
    	logger.info("username::::" + userName);
        return (Users) sessionFactory.getCurrentSession().
                createCriteria(Users.class).
                add(Restrictions.eq("userEmail", userName)).uniqueResult();
    }
    
    @Transactional
    @Override
    public Users getUserByUserNameWithAuthorities(String userName) {
    	logger.info("username::::" + userName);
        return (Users) sessionFactory.getCurrentSession().
                createCriteria(Users.class).
                add(Restrictions.eq("userEmail", userName)).
                setFetchMode("authoritiesCollection", FetchMode.JOIN).
                uniqueResult();
    }

    @Transactional
	@Override
	public Users save(Users user) {
		sessionFactory.getCurrentSession().save(user);
        return user;
	}

    @Transactional
    @Override
    public Users update(Users user) {
    	sessionFactory.getCurrentSession().update(user);
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

	@SuppressWarnings("unchecked")
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
	}
}
*/