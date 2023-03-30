package com.java.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED)
public class UserCredentialsRepositoryImpl implements UserCredentialsRepositoryCustom {

	@Autowired EntityManager entityManager;
	//TODO can fix it to delete specific entry
	@Override
	public void deleteCredential() {
		Query query = entityManager.createNativeQuery("delete from user_credential_roles");
		query.executeUpdate();
		
	}

	
}
