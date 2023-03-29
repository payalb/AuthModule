package com.java.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.dto.UserCredential;

public interface UserCredentialsRepository extends JpaRepository<UserCredential, String>{

}
