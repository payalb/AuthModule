package com.java.service;

import java.util.List;

import com.java.dto.Role;


public interface RolesService {
	public Role save(Role role) ;

	public List<Role> getAllRoles();


}
