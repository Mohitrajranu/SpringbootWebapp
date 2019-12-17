package com.restfulservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulservice.model.FreeTrialUser;

// TODO: Auto-generated Javadoc
/**
 * The Interface FreeTrialRepository.
 *  @author Mohit Raj
 */
@Repository
@Transactional
public interface FreeTrialRepository extends JpaRepository<FreeTrialUser, Integer>{

	/**
	 * Find by id.
	 *
	 * @param id the id
	 * @return the free trial user
	 */
	FreeTrialUser findById(int id);
	
	/**
	 * Find by username.
	 *
	 * @param username the username
	 * @return the list
	 */
	//FreeTrialUser findByUsername(String username);
	List<FreeTrialUser> findByUsername(String username);
	
	/**
	 * Find by username and product type.
	 *
	 * @param username the username
	 * @param productType the product type
	 * @return the free trial user
	 */
	FreeTrialUser findByUsernameAndProductType(String username,String productType);
	
	/**
	 * Count by username.
	 *
	 * @param username the username
	 * @return the long
	 */
	Long countByUsername(String username);
	
	/**
	 * Delete by username.
	 *
	 * @param username the username
	 * @return the long
	 */
	Long deleteByUsername(String username);
	
	/**
	 * Exists by username.
	 *
	 * @param username the username
	 * @return true, if successful
	 */
	boolean existsByUsername(String username);
	
	/**
	 * Exists by username and product type.
	 *
	 * @param username the username
	 * @param productType the product type
	 * @return true, if successful
	 */
	boolean existsByUsernameAndProductType(String username,String productType);
	
	/**
	 * Delete free trial user by id.
	 *
	 * @param id the id
	 */
	void deleteFreeTrialUserById(int id);
}
