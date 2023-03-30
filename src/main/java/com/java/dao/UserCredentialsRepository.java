package com.java.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.java.dto.UserCredential;

public interface UserCredentialsRepository extends JpaRepository<UserCredential, String>{

	Optional<UserCredential> findByUsername(String username);
	
	@Query(value = "delete from user_credential")
	void deleteCredentialRoleEntry();

}
