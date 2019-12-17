package com.restfulservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulservice.model.UserToken;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserTokenRepository.
 * @author Mohit Raj
 */
@Repository("userTokenRepository")
public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {
	 
 	/**
 	 * Find by email.
 	 *
 	 * @param email the email
 	 * @return the optional
 	 */
 	Optional<UserToken> findByEmail(String email);
	 
 	/**
 	 * Find by reset token.
 	 *
 	 * @param resetToken the reset token
 	 * @return the optional
 	 */
 	Optional<UserToken> findByResetToken(String resetToken);
}