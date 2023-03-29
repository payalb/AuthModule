package com.java.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name = "UserInfo")
public class User {

	@Id
	private String username;
	@NotEmpty
	private String password;
	@NotEmpty
	@ManyToMany(fetch = FetchType.EAGER)
	//@Cascade(CascadeType.SAVE_UPDATE)
	private List<Role> roles;
	
}
