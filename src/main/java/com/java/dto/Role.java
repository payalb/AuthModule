package com.java.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name= "userrole")
public class Role {
	@Id
	private String rname;
	
	@EqualsAndHashCode.Exclude
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
	@JoinTable(name = "role_privilege",
	joinColumns = @JoinColumn(name = "rname", referencedColumnName = "rname"),
	inverseJoinColumns = @JoinColumn(name = "pname", referencedColumnName = "pname"))
	private List<Privilege> privileges = new ArrayList<>();
	
	public Role(String rname){
		this.rname= rname;
	}

	
}
