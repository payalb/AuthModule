package com.java.config;

import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.java.dto.UserCredential;
import com.java.service.UserCredentialService;


@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    UserCredentialService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserCredential> optionalUserCredential = userService.findByUsername(username);
        if(optionalUserCredential== null){
            throw new UsernameNotFoundException("Invalid user name or password.");
        }
        UserCredential userCredential= optionalUserCredential.get();
        return new org.springframework.security.core.userdetails.User(userCredential.getUsername(), userCredential.getPassword(), userCredential.getRoles().stream().map(x-> new SimpleGrantedAuthority(x.getRname())).collect(Collectors.toList()));
    }
}
