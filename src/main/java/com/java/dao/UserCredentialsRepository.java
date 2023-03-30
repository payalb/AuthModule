package com.java.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.dto.UserCredential;

public interface UserCredentialsRepository extends JpaRepository<UserCredential, String>, UserCredentialsRepositoryCustom{

	Optional<UserCredential> findByUsername(String username);
	
	/*
	 * @Modifying
	 * 
	 * @Query(value = "delete from user_credential_roles") void deleteCredential();
	 */
}
