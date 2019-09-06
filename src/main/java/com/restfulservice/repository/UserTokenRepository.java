package com.restfulservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulservice.model.UserToken;

@Repository("userTokenRepository")
public interface UserTokenRepository extends JpaRepository<UserToken, Integer> {
	 Optional<UserToken> findByEmail(String email);
	 Optional<UserToken> findByResetToken(String resetToken);
}