package com.java.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Role {
	@Id
	private String rname;
	
	@EqualsAndHashCode.Exclude
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_privilege",
	joinColumns = @JoinColumn(name = "rname", referencedColumnName = "rname"),
	inverseJoinColumns = @JoinColumn(name = "pname", referencedColumnName = "pname"))
	private List<Privilege> privileges = new ArrayList<>();
	
	public Role(String rname){
		this.rname= rname;
	}

	
}
