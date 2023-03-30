package com.java.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.java.dto.Privilege;
import com.java.service.PrivilegeService;


/**
 * Not exposing this to Swagger, but we can if needed.
 * @author Payal Bansal
 * 
 */
@RestController
@RequestMapping("/v1/privileges")
public class PrivilegesController {

	@Autowired
	PrivilegeService service;

	@PostMapping
	public ResponseEntity<Object> createPrivilege(@RequestBody Privilege privilege) {
		service.save(privilege);
		Link link = WebMvcLinkBuilder.linkTo(PrivilegesController.class, privilege.getPname()).withRel("self");
		return ResponseEntity.created(link.toUri()).build();
	}
	
	@GetMapping
	public ResponseEntity<List<Privilege>> getAllPrivileges() {
		return ResponseEntity.ok(service.getAllPrivileges());
	}
}
