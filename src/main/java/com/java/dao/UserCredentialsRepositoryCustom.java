package com.java.dao;

import org.springframework.transaction.annotation.Transactional;

public interface UserCredentialsRepositoryCustom {

	//TODO can set it up in test profile alone or fix it to delete particular user details

	void cleanup();
}

