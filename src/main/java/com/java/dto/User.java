package com.java.dto;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.hateoas.RepresentationModel;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Table(name= "userinfo")
@Entity
@NoArgsConstructor
public class User extends RepresentationModel<User>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	@NotEmpty
	@Size(min = 3, max = 30)
	private String name;
	private String address;
	private long phoneNumber;
	//Using OneToOne instead of Embedded to provide database table level grants
	@OneToOne(fetch = FetchType.EAGER)
	private UserCredentials userCredentials;

}
