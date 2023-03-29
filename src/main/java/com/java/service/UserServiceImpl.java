package com.java.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.java.dao.UserRepository;
import com.java.dto.User;

@Service
public class UserServiceImpl implements UserService{

	@Autowired UserRepository repository;

	@Override
	public User save(User user) {
		return repository.save(user);
	}

	@Override
	public Page<User> getAllUsers(int offset, int limit) {
		return repository.findAll(PageRequest.of(offset, limit, Sort.by("username")));
	}
	
	
}
