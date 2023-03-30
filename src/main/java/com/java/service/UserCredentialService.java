package com.java.service;

import java.util.Optional;

import com.java.dto.UserCredential;


public interface UserCredentialService {


	public Optional<UserCredential> findByUsername(String username);

	public Optional<UserCredential> findById(String username);

}
