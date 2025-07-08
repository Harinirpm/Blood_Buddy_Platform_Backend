package com.bbp.BBPlatform.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.bbp.BBPlatform.model.UserEntity;
import com.bbp.BBPlatform.repository.UserEntityRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{
	@Autowired
	private UserEntityRepository userDetailsrepo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userDetailsrepo.findByEmail(email)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		
		return new CustomUserDetailsPrincipal(userEntity);
	}

}
