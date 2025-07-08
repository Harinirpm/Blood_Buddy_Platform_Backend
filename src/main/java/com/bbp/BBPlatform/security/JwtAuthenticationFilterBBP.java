package com.bbp.BBPlatform.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilterBBP extends OncePerRequestFilter{
//Blood Buddy Platform authFilter
	private final JwtUtilBBP jwtUtil;
	private final CustomUserDetailsService customUserDetailsService;
	
	public JwtAuthenticationFilterBBP(JwtUtilBBP jwtUtil, CustomUserDetailsService customUserDetailsService) {
		this.jwtUtil = jwtUtil;
		this.customUserDetailsService = customUserDetailsService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String authHeader = request.getHeader("Authorization");
		if(authHeader == null || !authHeader.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
		}
		String token = authHeader.substring(7);
		String userEmail = jwtUtil.getEmailFromToken(token);
		if(userEmail != null && SecurityContextHolder.getContext().getAuthentication()==null) {
			//default Spring "UserDetails"
			UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);
			System.out.println("User Details : "+userDetails);
		    //check is token expired
			if(!jwtUtil.isTokenExpired(token)) {
				//extarct role from the token
				String role = jwtUtil.getRoleFromToken(token);
				System.out.println("Role :+ "+role);
				//create authenticationToken
				UsernamePasswordAuthenticationToken authToken = new 
						UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
				
				//attach meta data about the http request
				authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				System.out.println("Auth token from jwtAuthFilter: "+authToken);
				SecurityContextHolder.getContext().setAuthentication(authToken);
			}
		}
		filterChain.doFilter(request, response);
	}
	
	
	
}
