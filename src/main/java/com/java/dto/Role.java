package com.java.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor

public class Role {
	@Id
	private String rname;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_privilege",
	joinColumns = @JoinColumn(name = "rname", referencedColumnName = "rname"),
	inverseJoinColumns = @JoinColumn(name = "pname", referencedColumnName = "pname"))

	private List<Privilege> privileges;
	
	public Role(String name){
		this.rname= name;
	}

	
}
