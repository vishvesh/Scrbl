package com.scrbl.service.impl;

import org.apache.log4j.Logger;

public class UsersServiceImpl extends AbstractService {// implements UsersService {

	Logger logger = Logger.getLogger(getClass());
	
	/*private ShaPasswordEncoder userPasswordEncoder;
    
    public void setUserPasswordEncoder(ShaPasswordEncoder userPasswordEncoder) {
		this.userPasswordEncoder = userPasswordEncoder;
	}

    
    @Transactional
	@Override
	public Users saveNewUser(Users user) {
    	String unEncryptedPassword = user.getUserPassword();
    	String encryptedPassword = userPasswordEncoder.encodePassword(unEncryptedPassword, user.getUserEmail());
    	user.setUserPassword(encryptedPassword);
		Users newUser = userDao.save(user);
    	Authorities authorities = new Authorities();
    	authorities.setAuthority(AppConstants.ROLE_USER_ROLE);
    	authorities.setUsersId(newUser);
    	authoritiesDao.save(authorities);
		return newUser;
	}
    
    @Transactional
    @Override
    public Users getUserByUserName(String userName) {
    	return userDao.getUserByUserNameWithAuthorities(userName);
    }

    @Transactional
	@Override
	public List<Users> searchUser(String loggedInUser, String namesToSearch) {
		return userDao.searchUser(loggedInUser, namesToSearch);
	}

    @Transactional
	@Override
	public Users getUserById(Long Id) {
		return userDao.getById(Id);
	}

    @Transactional
    @Override
    public Users updateUser(Users user) {
    	return userDao.update(user);
    }
    
    @Transactional
    @Override
    public Users resetUserPassword(Users user) {
    	String unEncryptedPassword = user.getUserPassword();
    	String encryptedPassword = userPasswordEncoder.encodePassword(unEncryptedPassword, user.getUserEmail());
    	user.setUserPassword(encryptedPassword);
		user.setPasswordNeedsChange("YES");
		user.setEnabled(false);
		return userDao.update(user);
    }
    
    @Transactional
    @Override
    public Users changeUserPassword(Users user) {
    	String unEncryptedPassword = user.getUserPassword();
    	String encryptedPassword = userPasswordEncoder.encodePassword(unEncryptedPassword, user.getUserEmail());
    	user.setUserPassword(encryptedPassword);
		user.setPasswordNeedsChange(null);
		user.setEnabled(true);
		return userDao.update(user);
    }*/
}
