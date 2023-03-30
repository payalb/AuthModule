package com.java.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.java.dao.PrivilegeRepository;
import com.java.dao.RoleRepository;
import com.java.dao.UserRepository;
import com.java.dto.Privilege;
import com.java.dto.Role;
import com.java.dto.User;
import com.java.dto.UserCredential;
import com.java.dto.UserInput;


@SpringBootTest
@AutoConfigureMockMvc

public class UserServiceIT {

	@Autowired
	  private MockMvc mockMvc;

	  @Autowired
	  private ObjectMapper objectMapper;
	  @Autowired UserService service;

	  @Autowired
	  private UserRepository userRepository;
	  
	  @Autowired RolesService rolesService;
		
	  @Autowired PrivilegeRepository privilegeRepository;
	  
	  @Autowired PasswordEncoder encoder;
	  /**
	   * Test the user object and roles object saved properly. Privileges should not be there.
	   * Creating a user with roles, but no privileges assigned yet
	   * @throws JsonProcessingException
	   * @throws Exception
	   */
	  
	  @Test
		 @WithMockUser(username="payal123!",authorities = {"ADMIN"})
		  public void createUser() throws JsonProcessingException, Exception {
			  System.out.println("In createUser!");
			  User user= new User();
			  user.setUserId(1);
			   user.setName("Payal");
			   user.setAddress("10R delhi road");
			   user.setPhoneNumber(7326786232l);
			   UserCredential credential= new UserCredential();
			   credential.setPassword(encoder.encode("abc"));
			   credential.setUsername("abc");
			   List<Role> rolesList= new ArrayList<>();
			   rolesList.add(new Role("ADMIN"));
			   credential.setRoles(rolesList);
			   user.setUserCredential(credential);
			   
			   service.save(user);
			   
			   List<User> userObjects = userRepository.findAll();
			   assertThat(userObjects.size()).isEqualTo(1);
			   User objUser= userObjects.get(0);
			   assertThat(objUser.getUserId()).isNotEqualTo(0);
			   assertThat(objUser.getName()).isEqualTo("Payal");
			   List<Role> roles= objUser.getUserCredential().getRoles();
			   assertThat(roles).containsAnyOf(new Role("ADMIN"));
			   assertThat(roles.stream().flatMap(role-> role.getPrivileges().stream()).collect(Collectors.toList())).isEmpty();
			   
		  }
	  
	 @Test
	 @WithMockUser(username="payal123!",authorities = {"ADMIN"})
	// @Disabled("For testing")
	  public void createUserWithRolesButNoPrivileges() throws JsonProcessingException, Exception {
		  System.out.println("In createUser!");
		  UserInput user= new UserInput();
		   user.setName("Payal");
		   user.setAddress("10R delhi road");
		   user.setPhoneNumber(7326786232l);
		   user.setPassword(encoder.encode("abc"));
		   user.setUsername("abc");
		   user.setRoles(List.of(new Role("ADMIN")));
		   
		   
		   mockMvc.perform(post("/v1/users")
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
	 @WithMockUser(username="payal123",authorities = {"ADMIN"})
	  @Test
	  public void createUserWithoutPrivilegesLaterAssignPrivilegesToRole() throws JsonProcessingException, Exception {
		  System.out.println("In createUser!");
		  UserInput user= new UserInput();
		   user.setName("Payal");
		   user.setAddress("10R delhi road");
		   user.setPhoneNumber(7326786232l);
		   user.setPassword(encoder.encode("abc"));
		   user.setUsername("abc");
		   user.setRoles(List.of(new Role("ADMIN")));
		   
		   
		   mockMvc.perform(post("/v1/users")
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
	  
	  @Test
	  @WithMockUser(username="payal123",authorities = {"ADMIN"})
	  public void createUserTestForTransactions() throws JsonProcessingException, Exception {
		  System.out.println("In createUser!");
		  UserInput user= new UserInput();
		   user.setName("Payal");
		   user.setAddress("10R delhi road");
		   user.setPhoneNumber(7326786232l);
		   user.setPassword(encoder.encode("abc"));
		   user.setUsername("abc");
		   user.setRoles(List.of(new Role("ADMIN", List.of(new Privilege("DB_WRITE"), new Privilege("DB_READ")))));
		   RoleRepository roleRepository= Mockito.mock(RoleRepository.class);
		   when(roleRepository.save(any())).thenThrow(new RuntimeException());
		   try {
		   mockMvc.perform(post("/v1/users")
		            .contentType("application/json")
		            .content(objectMapper.writeValueAsString(user)))
		            .andExpect(status().isCreated());
		   }catch(Exception e) {}
		 
		   List<User> userObjects = userRepository.findAll();
		   assertThat(userObjects.size()).isEqualTo(0);
		   assertThat(roleRepository.findAll().size()).isEqualTo(0);
		   assertThat(privilegeRepository.findAll().size()).isEqualTo(0);
	  }
}
