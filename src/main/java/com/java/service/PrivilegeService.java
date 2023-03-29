package com.java.service;

import java.util.List;

import com.java.dto.Privilege;


public interface PrivilegeService {
	public Privilege save(Privilege privilege) ;

	public List<Privilege> getAllPrivileges();


}
