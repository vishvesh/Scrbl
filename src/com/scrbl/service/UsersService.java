package com.scrbl.service;

import java.util.List;

import com.scrbl.model.Users;

public interface UsersService {

	/*public Users saveNewUser(Users user);
	
	public Users getUserByUserName(String userName);
	
	public List<Users> searchUser(String loggedInUser, String namesToSearch);
	
	public Users getUserById(Long Id);
	
	public Users updateUser(Users user);
	
	public Users changeUserPassword(Users user);
	
	public Users resetUserPassword(Users user);*/
	
	public List<Users> getAllUsers();
	
	public Users saveNewUser(Users user);
	
	public Users getUserByEmail(String userName);
}
