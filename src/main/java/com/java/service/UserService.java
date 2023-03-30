package com.java.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.java.dto.User;


public interface UserService {
	@Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
	public User save(User user) ;

	public Page<User> getAllUsers(int offset, int limit);

	public Optional<User> findUserById(int id);

}
