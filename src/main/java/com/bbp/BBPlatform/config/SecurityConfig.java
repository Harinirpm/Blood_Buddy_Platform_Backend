package com.bbp.BBPlatform.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.bbp.BBPlatform.repository.UserEntityRepository;
import com.bbp.BBPlatform.security.CustomUserDetailsService;
import com.bbp.BBPlatform.security.JwtAuthenticationFilterBBP;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	
	private final UserEntityRepository userDetailsRepo;
	private final JwtAuthenticationFilterBBP jwtFilter;
	private final CustomUserDetailsService customUserDetailsService;
	
	public SecurityConfig(UserEntityRepository userDetailsRepo,CustomUserDetailsService customUserDetailsService,JwtAuthenticationFilterBBP jwtFilter) {
		this.userDetailsRepo = userDetailsRepo;
		this.customUserDetailsService = customUserDetailsService;
		this.jwtFilter = jwtFilter;
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http
				.cors(Customizer.withDefaults())
				.csrf(Customizer -> Customizer.disable())
				.authorizeHttpRequests(request -> request
						.requestMatchers("/register/**", "/auth/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/donar/**").hasAnyRole("ADMIN","DONAR","HOSPITAL")
						.requestMatchers("/hospital/**").hasAnyRole("ADMIN","HOSPITAL")
						.anyRequest().authenticated())
				.formLogin(AbstractHttpConfigurer::disable)
				.httpBasic(Customizer.withDefaults())
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authenticationProvider(authenticationProvider())
	            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
	            .build();			
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncode() {
		return new BCryptPasswordEncoder(12);
	}
	
	//this authentication manages used in 'AuthenticationService' for explicit authentication
	 @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManagerBuilder.class)
	                .userDetailsService(customUserDetailsService)
	                .passwordEncoder(passwordEncode())
	                .and()
	                .build();
	        return authenticationManager;
	    }
	 
	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncode());
		provider.setUserDetailsService(customUserDetailsService);
		return provider;
	}
	 @Bean
	    public UrlBasedCorsConfigurationSource corsConfigurationSource() {
	        CorsConfiguration config = new CorsConfiguration();
	        config.addAllowedOrigin("http://localhost:5173");
	        config.addAllowedMethod("*");
	        config.addAllowedHeader("*");
	        config.setAllowCredentials(true);

	        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	        source.registerCorsConfiguration("/**", config);
	        return source;
	    }
}
