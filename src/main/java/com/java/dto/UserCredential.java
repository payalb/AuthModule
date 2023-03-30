package com.java.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.spi.ValidationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCredential {

	@Id
	private String username;
	@NotEmpty
	@ToString.Exclude
	private String password;
	
	@NotEmpty
	@ManyToMany(fetch = FetchType.EAGER)
	//@Cascade(CascadeType.SAVE_UPDATE)
	@Builder.Default
	private List<Role> roles= new ArrayList<>();
	

}
