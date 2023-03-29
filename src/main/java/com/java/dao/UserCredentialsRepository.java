package com.java.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.dto.UserCredentials;

public interface UserCredentialsRepository extends JpaRepository<UserCredentials, String>{

}
