package com.restfulservice.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulservice.model.FreeTrialUser;

@Repository
@Transactional
public interface FreeTrialRepository extends JpaRepository<FreeTrialUser, Integer>{

	FreeTrialUser findById(int id);
	//FreeTrialUser findByUsername(String username);
	List<FreeTrialUser> findByUsername(String username);
	FreeTrialUser findByUsernameAndProductType(String username,String productType);
	Long countByUsername(String username);
	Long deleteByUsername(String username);
	boolean existsByUsername(String username);
	boolean existsByUsernameAndProductType(String username,String productType);
	void deleteFreeTrialUserById(int id);
}
