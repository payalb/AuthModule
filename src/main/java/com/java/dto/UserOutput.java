package com.java.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
	private Map<String, List<String>> roles;

	private UserOutput() {
	}

	/**
	 * Factory method to return useroutput object
	 * 
	 * @param user
	 * @return
	 */
	public static UserOutput getInstance(User user) {
		 Map<String, List<String>> roles= new HashMap<>();
		 user.getUserCredential().getRoles().stream().forEach(x-> {
			try {
				roles.put(x.getRname(), x.getPrivileges().stream().map((Privilege y)-> y.getPname()).collect(Collectors.toList()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		System.out.println("user credential is "+ user.getUserCredential());
		return UserOutput.builder().name(user.getName()).userId(user.getUserId()).address(user.getAddress())
				.phoneNumber(user.getPhoneNumber()).roles(roles).build();
	}
}
