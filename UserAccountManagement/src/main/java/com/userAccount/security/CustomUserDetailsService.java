package com.userAccount.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.userAccount.model.User;
import com.userAccount.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {
    
	@Autowired
	private UserRepository repo;
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		
		User user = repo.findByEmail(email);
		if(user ==null) {
			
			throw new UsernameNotFoundException("User not found");
		}
		return new CurrentUserDetails(user);
	}

}
