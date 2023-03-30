package com.java.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.dao.UserRepository;
import com.java.dto.Privilege;
import com.java.dto.Role;
import com.java.dto.User;
import com.java.dto.UserInput;


@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceIT {

	@Autowired
	  private MockMvc mockMvc;

	  @Autowired
	  private ObjectMapper objectMapper;

	  @Autowired
	  private UserRepository userRepository;
	  
	  @Autowired RolesService rolesService;
		
	  /**
	   * Test the user object and roles object saved properly. Privileges should not be there.
	   * Creating a user with roles, but no privileges assigned yet
	   * @throws JsonProcessingException
	   * @throws Exception
	   */
	//  @Test
	  public void createUserWithRolesButNoPrivileges() throws JsonProcessingException, Exception {
		  System.out.println("In createUser!");
		  UserInput user= new UserInput();
		   user.setName("Payal");
		   user.setAddress("10R delhi road");
		   user.setPhoneNumber(7326786232l);
		   user.setPassword("abc");
		   user.setUsername("abc");
		   user.setRoles(List.of(new Role("ADMIN")));
		   
		   
		   mockMvc.perform(post("/users")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(user)))
		            .andExpect(status().isCreated());
		   
		   List<User> userObjects = userRepository.findAll();
		   assertThat(userObjects.size()).isEqualTo(1);
		   User objUser= userObjects.get(0);
		   assertThat(objUser.getUserId()).isNotEqualTo(0);
		   assertThat(objUser.getName()).isEqualTo("Payal");
		   List<Role> roles= objUser.getUserCredential().getRoles();
		   assertThat(roles).containsAnyOf(new Role("ADMIN"));
		   assertThat(roles.stream().flatMap(role-> role.getPrivileges().stream()).collect(Collectors.toList())).isEmpty();
		   
	  }
	  
	  /**
	   * Test the user object and roles object saved properly. Privileges should not be there.
	   * Later assign privilege to the role
	   * Creating a user with roles. Privileges should be assigned to the user
	   * @throws JsonProcessingException
	   * @throws Exception
	   */
	  @Test
	  public void createUserWithoutPrivilegesLaterAssignPrivilegesToRole() throws JsonProcessingException, Exception {
		  System.out.println("In createUser!");
		  UserInput user= new UserInput();
		   user.setName("Payal");
		   user.setAddress("10R delhi road");
		   user.setPhoneNumber(7326786232l);
		   user.setPassword("abc");
		   user.setUsername("abc");
		   user.setRoles(List.of(new Role("ADMIN")));
		   
		   
		   mockMvc.perform(post("/users")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(user)))
		            .andExpect(status().isCreated());
		   
		 
		   Role role = new Role("ADMIN", List.of(new Privilege("DB_READ"), new Privilege("DB_WRITE")));
		   rolesService.save(role);
		   
		   List<User> userObjects = userRepository.findAll();
		   assertThat(userObjects.size()).isEqualTo(1);
		   User objUser= userObjects.get(0);
		   assertThat(objUser.getUserId()).isNotEqualTo(0);
		   assertThat(objUser.getName()).isEqualTo("Payal");
		   List<Role> roles= objUser.getUserCredential().getRoles();
		   assertThat(roles).containsAnyOf(new Role("ADMIN"));
		   assertThat(roles.stream().flatMap(r-> r.getPrivileges().stream()).collect(Collectors.toList())).contains(new Privilege("DB_READ"));
		   
		   
	  }
}
