package com.java.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
public class UserCredentials {

	@Id
	private String username;
	@NotEmpty
	@ToString.Exclude
	private String password;
	@NotEmpty
	@ManyToMany(fetch = FetchType.EAGER)
	//@Cascade(CascadeType.SAVE_UPDATE)
	private List<Role> roles;
	
}
