package com.java.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.java.config.JwtTokenUtil;
import com.java.dto.User;
import com.java.dto.UserCredential;
import com.java.dto.UserInput;
import com.java.dto.UserOutput;
import com.java.service.UserCredentialService;
import com.java.service.UserService;



@RestController
@RequestMapping(path = "/v1/users")
public class UserController {
	
	@Autowired UserService userService;
	@Autowired UserCredentialService userCredentialService;
	@Autowired JwtTokenUtil tokenUtil;
	@Autowired PasswordEncoder encoder;
	
	Logger logger= Logger.getLogger(UserController.class.getName());
	/**
	 * Currently only admin can create a user. It will also create necessary roles and privileges
	 *  
	 * @param userInput
	 * @return
	 * @throws URISyntaxException
	 */
	@PreAuthorize("hasAuthority('ADMIN')")
	@PostMapping
	public ResponseEntity<Void> createUser(@RequestBody UserInput userInput) throws URISyntaxException{
		 User user= userService.save(UserInput.getUserObject(userInput));
		 URI pathString = WebMvcLinkBuilder.linkTo(UserController.class).slash(user.getUserId()).toUri()
				 ;
		 return ResponseEntity.created(pathString).build();
	}
	
	/**
	 * This method returns the paginated user data  in ascending order of id.
	 * This does not return back the user credentials
	 * If no content found, returns just the status as 204. No body
	 * @param offset: By default starts with 1st record
	 * @param limit: By default fetches 20 records
	 * @return Page of user
	 */
	@GetMapping
	public ResponseEntity<List<UserOutput>> getAllUsers(@RequestParam(name = "offset", defaultValue = "0") int offset, @RequestParam(name = "limit", defaultValue = "20") int limit) {
		System.out.println(userService+ " is userservice");
		List<User> users = userService.getAllUsers(offset, limit).getContent();
		if(users.isEmpty()) {
			return ResponseEntity.noContent().build();
		}else {
			List<UserOutput> uoList = users.stream().map(user-> UserOutput.getInstance(user)).collect(Collectors.toList());
			return ResponseEntity.ok(uoList);
		}
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserOutput> getUser(@PathVariable(name = "userId") int userId) {
		Optional<User> optionalUser = userService.findUserById(userId);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			/*
			 * Since we would be doing eager fetch, not using links here.
			 */
		//	Link link= WebMvcLinkBuilder.linkTo(methodOn(UserController.class).getUser(userId)).slash("roles").withRel("roles");
			//user.add(link);
			return ResponseEntity.ok(UserOutput.getInstance(user));
		}else {
			return ResponseEntity.noContent().build();
		}
	}

	/**
	 * This method has public access and takes in username, password and generates an access token
	 * @param user
	 * @return access token
	 */
	@PostMapping(path = "/login")
	  public ResponseEntity<?> loginUser(@RequestBody UserCredential user) {
	      if(user.getUsername() == null || user.getPassword() == null) {
	      throw new RuntimeException("UserName or Password is Empty");
	    }
	    Optional<UserCredential> userData = userCredentialService.findById(user.getUsername());
	    if(userData.isEmpty() || !encoder.matches( user.getPassword(), userData.get().getPassword() )){
	       throw new RuntimeException("UserName or Password is Invalid");
	    }
	       return new ResponseEntity<>(tokenUtil.generateAccessToken(user), HttpStatus.OK);

	    }
}
