/*package com.scrbl.dao;

import java.util.Collection;
import java.util.List;

import org.lazypizza.data.model.Authorities;
import org.lazypizza.data.model.Users;

public interface UsersDao extends GenericDao<Users> {

    public Users getUserByUserName(String userName);
    
    public Users update(Users user);
    
    public List<Users> searchUser(String loggedInUser, String namesToSearch);
	
    *//**
     * Gets the instance of {@link Users} identified by userName. This method
     * also provides a {@link Collection} of {@link Authorities} that is eagerly
     * fetched so as not to have any lazy loading exception.
     * 
     * @param userName
     * @return
     *//*
    public Users getUserByUserNameWithAuthorities(String userName);
}
*/