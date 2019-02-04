package com.spring.security.springsecurity.service;

import com.spring.security.springsecurity.model.User;
import com.spring.security.springsecurity.model.UserDetailsImpl;
import com.spring.security.springsecurity.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> usersOptional = userRepository.findByName(username);
        usersOptional
                .orElseThrow(() -> new UsernameNotFoundException("Username not Found"));
       return usersOptional.map(UserDetailsImpl::new).get();
    }
}
