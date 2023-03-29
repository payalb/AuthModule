package com.java.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.java.dto.Privilege;

/*@RepositoryRestResource*/
public interface PrivilegeRepository extends JpaRepository<Privilege, String>{

}
