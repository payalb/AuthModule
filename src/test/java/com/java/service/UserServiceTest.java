
package com.java.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.java.dao.PrivilegeRepository;
import com.java.dao.RoleRepository;
import com.java.dao.UserCredentialsRepository;
import com.java.dao.UserRepository;
import com.java.dto.Role;
import com.java.dto.User;
import com.java.dto.UserCredential;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

	@TestConfiguration
	static class TestConfig {

		@Bean
		public UserService userService() {
			return new UserServiceImpl();
		}
	}

	@Autowired
	UserService userService;

	@MockBean UserRepository repository;
	@MockBean UserCredentialsRepository userCredentialsRepository;
	@MockBean RoleRepository roleRepository;
	@MockBean PrivilegeRepository privilegeRepository;
	

	@Test
	public void testFindByName() {
		User user = new User();
		user.setName("Payal");
		user.setAddress("10R delhi road");
		user.setPhoneNumber(7326786232l);
		UserCredential credential = new UserCredential();
		credential.setPassword("abc");
		credential.setUsername("abc");
		credential.setRoles(List.of(new Role("ADMIN")));
		user.setUserCredential(credential);
		when(repository.save(user)).thenReturn(user);
		assertThat(userService.save(user)).isEqualTo(user);
	}

}
