package com.java.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.java.dto.Privilege;
import com.java.dto.Role;
import com.java.dto.User;
import com.java.dto.UserCredential;
import com.java.service.UserService;

/**
 * This will insert dummy data into the user table, with admin credentials. 
 * Can be used to create users.
 * @author Payal Bansal
 *
 */
@Component
public class RootUserInitializer implements CommandLineRunner{

	@Autowired UserService service;
	@Override
	public void run(String... args) throws Exception {
		   User user= new User();
		   user.setName("Payal");
		   user.setAddress("10R delhi road");
		   user.setPhoneNumber(7326786232l);
		   UserCredential credential = new UserCredential();
		   credential.setPassword("payal123!");
		   credential.setUsername("payal123!");
		   credential.setRoles(List.of(new Role("ADMIN", List.of(new Privilege("DB_WRITE"), new Privilege("DB_READ")))));
		   user.setUserCredential(credential);
		   service.save(user);
		   
		
	}

}
