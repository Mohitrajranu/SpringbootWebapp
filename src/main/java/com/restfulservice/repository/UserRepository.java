package com.restfulservice.repository;


import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulservice.model.User;
// TODO: Auto-generated Javadoc

/**
 * The Interface UserRepository.
 * @author Mohit Raj
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Integer>  {

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the user
	 */
	User findById(int id);
	
	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the user
	 */
	//username
	User findByUsername(String username);
	
	/**
	 * Find by username and roleid.
	 *
	 * @param username the username
	 * @param roleid the roleid
	 * @return the user
	 */
	User findByUsernameAndRoleid(String username,int roleid);

	//void saveUser(User user);
	
	/**
	 * Count by username.
	 *
	 * @param username the username
	 * @return the long
	 */
	//void updateUser(User user);
	Long countByUsername(String username);
	
	/**
	 * Delete user by id.
	 *
	 * @param id the id
	 */
	void deleteUserById(int id);
	
	/**
	 * Exists by username.
	 *
	 * @param username the username
	 * @return true, if successful
	 */
	boolean existsByUsername(String username);
	
	/**
	 * Exists by username and roleid.
	 *
	 * @param username the username
	 * @param roleid the roleid
	 * @return true, if successful
	 */
	boolean existsByUsernameAndRoleid(String username,int roleid);
	//List<User> findAllUsers();
	
	//void deleteAllUsers();
	
	//boolean isUserExist(User user);
}
