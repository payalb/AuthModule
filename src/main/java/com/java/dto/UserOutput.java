package com.java.dto;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
public class UserOutput {

	private int userId;
	private String name;
	private String address;
	private long phoneNumber;
	private List<Role> roles;

	private UserOutput() {
	}

	/**
	 * Factory method to return useroutput object
	 * 
	 * @param user
	 * @return
	 */
	public static UserOutput getInstance(User user) {
		return UserOutput.builder().name(user.getName()).userId(user.getUserId()).address(user.getAddress())
				.phoneNumber(user.getPhoneNumber()).roles(user.getUserCredentials().getRoles()).build();
	}
}
