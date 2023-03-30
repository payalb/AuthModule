package com.java.dto;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name= "userinfo")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	@NotEmpty
	@Size(min = 3, max = 30)
	private String name;
	private String address;
	private long phoneNumber;
	//Using OneToOne instead of Embedded to provide database table level grants
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	private UserCredential userCredential;

}
