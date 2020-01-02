package com.restfulservice.service;

import java.util.List;
import java.util.Optional;

import com.restfulservice.model.UserToken;

// TODO: Auto-generated Javadoc
/**
 * The Interface UserService.
 * @author Mohit Raj
 */
public interface UserService {
	
	public List<UserToken> findByResetTokenIsNull();
	/**
	 * Find user by email.
	 *
	 * @param email the email
	 * @return the optional
	 */
	public Optional<UserToken> findUserByEmail(String email);
    
    /**
     * Find user by reset token.
     *
     * @param resetToken the reset token
     * @return the optional
     */
    public Optional<UserToken> findUserByResetToken(String resetToken);
    
    /**
     * Save.
     *
     * @param user the user
     */
    public void save(UserToken user);
    
    /**
     * Unsubscribe list.
     *
     * @param mailTemplate the mail template
     * @param unSubscribeEmail the un subscribe email
     * @param url the url
     */
    public  void unsubscribeList(String mailTemplate,String unSubscribeEmail,String url);
}
