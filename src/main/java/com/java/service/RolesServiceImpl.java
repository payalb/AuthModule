package com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dao.PrivilegeRepository;
import com.java.dao.RoleRepository;
import com.java.dto.Role;

@Service
public class RolesServiceImpl implements RolesService {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PrivilegeRepository privilegeRepository;

	@Override
	public Role save(Role role) {
		role.getPrivileges().forEach(p -> {
			try {
				privilegeRepository.save(p);
			} catch (Exception e) {
			}
		});
		return roleRepository.save(role);
	}

	@Override
	public List<Role> getAllRoles() {
		return roleRepository.findAll();
	}

}
