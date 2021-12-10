package com.github.patu11.chessservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity security) throws Exception {
		security.cors().and().csrf().disable();

		security
				.authorizeRequests()
				.antMatchers("/users/login").permitAll()
				.antMatchers("/users/signup").permitAll()
				.antMatchers("/users/all").permitAll()
				.antMatchers("/users/delete/**").permitAll()
				.antMatchers("/users/role/**").permitAll()
				.antMatchers("/profiles/**").permitAll()
				.antMatchers("/app/**").permitAll()
				.antMatchers("/games/**").permitAll()
				.antMatchers("/tournaments/**").permitAll()
				.anyRequest().authenticated()
				.and()
				.httpBasic();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return new MyUserDetailService();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
