package com.java.dao;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class UserCredentialsRepositoryImpl implements UserCredentialsRepositoryCustom {

	@Autowired EntityManager entityManager;
	//TODO can fix it to delete specific entry
	@Override
	public void cleanup() {
		Query query = entityManager.createNativeQuery("delete from role_privilege");
		query.executeUpdate();
		 query = entityManager.createNativeQuery("delete from privilege");
		query.executeUpdate();
		 query = entityManager.createNativeQuery("delete from user_credential_roles");
		query.executeUpdate();
		query = entityManager.createNativeQuery("update userinfo set user_credential_username = null");
		query.executeUpdate();
		 query = entityManager.createNativeQuery("delete from user_credential");
		query.executeUpdate();
		 query = entityManager.createNativeQuery("delete from userrole");
			query.executeUpdate();
		query = entityManager.createNativeQuery("delete from userinfo");
		query.executeUpdate();
	
		
	}

	
}
