package com.scrbl.dao;

import com.scrbl.model.Users;

public interface UsersDao extends GenericDao<Users> {

    public Users getUserByEmail(String email);

    //public List<Users> searchUser(String loggedInUser, String namesToSearch);
}
