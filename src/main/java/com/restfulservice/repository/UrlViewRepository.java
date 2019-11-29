package com.restfulservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restfulservice.model.UrlClicks;


public interface UrlViewRepository extends JpaRepository<UrlClicks, Integer> {

	Optional<UrlClicks> findByEmailAndTemplatenameAndUrl(String email,String templatename,String url);
	List<UrlClicks> findByEmailAndTemplatename(String email,String templatename);
	
	//findByUsernameAndProductType templatename url
}
