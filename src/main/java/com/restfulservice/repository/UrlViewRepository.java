package com.restfulservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.restfulservice.model.UrlClicks;


// TODO: Auto-generated Javadoc
/**
 * The Interface UrlViewRepository.
 * @author Mohit Raj
 */
public interface UrlViewRepository extends JpaRepository<UrlClicks, Integer> {

	/**
	 * Find by email and templatename and url.
	 *
	 * @param email the email
	 * @param templatename the templatename
	 * @param url the url
	 * @return the optional
	 */
	Optional<UrlClicks> findByEmailAndTemplatenameAndUrl(String email,String templatename,String url);
	
	/**
	 * Find by email and templatename.
	 *
	 * @param email the email
	 * @param templatename the templatename
	 * @return the list
	 */
	List<UrlClicks> findByEmailAndTemplatename(String email,String templatename);
	
	//findByUsernameAndProductType templatename url
}
