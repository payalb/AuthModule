package com.java.service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.java.dao.PrivilegeRepository;
import com.java.dao.RoleRepository;
import com.java.dao.UserCredentialsRepository;
import com.java.dao.UserRepository;
import com.java.dto.Privilege;
import com.java.dto.Role;
import com.java.dto.User;
import com.java.dto.UserCredential;


@Service
public class UserServiceImpl implements UserService{

	@Autowired UserRepository repository;
	@Autowired UserCredentialsRepository userCredentialsRepository;
	@Autowired RoleRepository roleRepository;
	@Autowired PrivilegeRepository privilegeRepository;
	
	Logger logger = Logger.getLogger(UserService.class.getName());

	@Override
	public User save(User user) {
		UserCredential credential = user.getUserCredential();
		List<Role> roles = credential.getRoles();
		roles.stream().flatMap(role -> role.getPrivileges().stream()).forEach(p -> 
		{
			Privilege privilege = privilegeRepository.save(p);
			logger.info(privilege + "is saved!");
		}
		);
		
		 roles.stream().forEach(role-> {
			 Role r = roleRepository.save(role);
			 logger.info(r + "is saved!");
		});
		
		UserCredential userCredential = userCredentialsRepository.save(credential);
		logger.info(userCredential + "is saved!");
		logger.info("user is "+ user);
		user.setUserCredential(userCredential);
		logger.info("user is "+ user);
		return repository.save(user);
		
	}

	@Override
	public Page<User> getAllUsers(int offset, int limit) {
		return repository.findAll(PageRequest.of(offset, limit, Sort.by("userId")));
	}

	@Override
	public Optional<User> findUserById(int  id) {
		return repository.findById(id);
	}
	
}
