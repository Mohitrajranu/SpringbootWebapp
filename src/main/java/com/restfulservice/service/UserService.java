package com.restfulservice.service;

import java.util.Optional;

import com.restfulservice.model.UserToken;

public interface UserService {
	public Optional<UserToken> findUserByEmail(String email);
    public Optional<UserToken> findUserByResetToken(String resetToken);
    public void save(UserToken user);
    public  void unsubscribeList(String mailTemplate,String unSubscribeEmail,String url);
}
