package com.bbp.BBPlatform.security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.bbp.BBPlatform.model.UserEntity;

@SuppressWarnings("serial")
public class CustomUserDetailsPrincipal implements UserDetails{
	
	private final UserEntity user;
	
	public CustomUserDetailsPrincipal(UserEntity user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singleton(new SimpleGrantedAuthority("ROLE_"+user.getRole().toUpperCase()));
		
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getEmail();
	}
	
	public String getName() {
		return user.getName();
	}
	public String getRole() {
		return "ROLE_"+user.getRole().toUpperCase();
	}
	
	
	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}
	@Override
	public boolean isAccountNonLocked() {
	    return true; 
	}
	@Override
	public boolean isCredentialsNonExpired() {
	    return true; 
	}
	@Override
	public boolean isEnabled() {
	    return true;
	}
	

}
