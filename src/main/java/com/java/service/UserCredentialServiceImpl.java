package com.java.service;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dao.UserCredentialsRepository;
import com.java.dto.UserCredential;


@Service
public class UserCredentialServiceImpl implements UserCredentialService{

	@Autowired UserCredentialsRepository userCredentialsRepository;
	
	Logger logger = Logger.getLogger(UserService.class.getName());

	
	@Override
	public Optional<UserCredential> findByUsername(String username) {
		return userCredentialsRepository.findById(username);
	}


	@Override
	public Optional<UserCredential> findById(String username) {
		return userCredentialsRepository.findById(username);
	}
	
	
}
