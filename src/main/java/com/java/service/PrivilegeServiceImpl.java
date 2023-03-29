package com.java.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.java.dao.PrivilegeRepository;
import com.java.dto.Privilege;

@Service
public class PrivilegeServiceImpl implements PrivilegeService{

	@Autowired PrivilegeRepository repository;

	@Override
	public Privilege save(Privilege privilege) {
		return repository.save(privilege);
	}

	@Override
	public List<Privilege> getAllPrivileges() {
		return repository.findAll();
	}
	
	
}
