package com.java.controller;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.Role;
import com.java.service.RolesService;

@RestController
@RequestMapping("/roles")
public class RolesController {

	Logger logger  = Logger.getLogger( RolesController.class.getName());
	@Autowired RolesService service;
	@PostMapping
	public ResponseEntity<Object> createRole(@RequestBody Role role)  {
		logger.info("Role is "+ role);
		service.save(role);
		Link link= WebMvcLinkBuilder.linkTo(RolesController.class).slash(role.getRname()).withRel("self");
		 return ResponseEntity.created(link.toUri()).build();
	}
	
	@GetMapping
	public ResponseEntity<List<Role>> getAllRoles() {
		return ResponseEntity.ok(service.getAllRoles());
	}
}
