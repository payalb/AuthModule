package com.java.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.java.dto.User;
import com.java.service.UserService;


@RestController
@RequestMapping(path = "users")
public class UserController {
	
	@Autowired UserService userService;
	
	@PostMapping
	public ResponseEntity<Void> createUser(@RequestBody User user) throws URISyntaxException{
		System.out.println("User received: "+ user);
		userService.save(user);
		 URI pathString = WebMvcLinkBuilder.linkTo(UserController.class).slash(user.getUsername()).toUri()
				 ;
		 return ResponseEntity.created(pathString).build();
	}
	
	/**
	 * This method returns the paginated user data  in ascending order of username.
	 * If no content found, returns just the status as 204. No body
	 * @param offset: By default starts with 1st record
	 * @param limit: By default fetches 20 records
	 * @return Page of user
	 */
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers(@RequestParam(name = "offset", defaultValue = "0") int offset, @RequestParam(name = "limit", defaultValue = "20") int limit) {
		System.out.println(userService+ " is userservice");
		List<User> users = userService.getAllUsers(offset, limit).getContent();
		if(users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok(users);
		}
	}

}
