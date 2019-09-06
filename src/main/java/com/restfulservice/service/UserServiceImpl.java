package com.restfulservice.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.restfulservice.model.UserToken;
import com.restfulservice.repository.UserTokenRepository;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserTokenRepository userTokenRepository;

	@Override
	public Optional<UserToken> findUserByEmail(String email) {
		return userTokenRepository.findByEmail(email);
	}

	@Override
	public Optional<UserToken> findUserByResetToken(String resetToken) {
		return userTokenRepository.findByResetToken(resetToken);
	}

	@Override
	public void save(UserToken userToken) {
		userTokenRepository.save(userToken);
	}
}