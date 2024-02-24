package com.project.usertask.project1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.project.usertask.project1.entities.User;
import com.project.usertask.project1.repository.UserRepository;

public class UserDetailsServiceimpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserName(username);
        if (user == null) {
            throw new UsernameNotFoundException("Could not found user");            
        }
        CustomUserDetails customUserDetails = new CustomUserDetails(user);
        return customUserDetails;
        
    }
    
}
