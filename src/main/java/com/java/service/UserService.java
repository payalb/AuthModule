package com.java.service;

import java.util.Optional;

import org.springframework.data.domain.Page;

import com.java.dto.User;


public interface UserService {
	public User save(User user) ;

	public Page<User> getAllUsers(int offset, int limit);

	public Optional<User> findUserById(int id);


}
