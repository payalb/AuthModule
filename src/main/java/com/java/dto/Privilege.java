package com.java.dto;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Privilege {
	@Id
	private String pname;
	
	@ManyToMany(mappedBy = "privileges")
	@JsonIgnore
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Set<Role> roles;
	
	public Privilege( String pname) {
		this.pname = pname;
	}

	
}
