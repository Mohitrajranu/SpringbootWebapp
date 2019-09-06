package com.restfulservice.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulservice.model.User;
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>  {

	User findById(int id);
	//username
	User findByUsername(String username);
	User findByUsernameAndRoleid(String username,int roleid);

	//void saveUser(User user);
	
	//void updateUser(User user);
	Long countByUsername(String username);
	void deleteUserById(int id);
	boolean existsByUsername(String username);
	boolean existsByUsernameAndRoleid(String username,int roleid);
	//List<User> findAllUsers();
	
	//void deleteAllUsers();
	
	//boolean isUserExist(User user);
}
