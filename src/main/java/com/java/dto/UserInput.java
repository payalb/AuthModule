package com.java.dto;

import java.util.List;

import lombok.Data;
import lombok.Getter;

@Getter
@Data
public class UserInput {



		private int userId;
		private String name;
		private String address;
		private long phoneNumber;
		private List<Role> roles;
		private String username;
		private String password;
		

		public static User  getUserObject(UserInput input) {
			return User.builder().name(input.getName()).userId(input.getUserId()).address(input.getAddress())
					.phoneNumber(input.getPhoneNumber()).userCredential(UserCredential.builder()
							.username(input.getUsername()).password(input.getPassword()).roles(input.getRoles()).build()).build();
		}
	}
