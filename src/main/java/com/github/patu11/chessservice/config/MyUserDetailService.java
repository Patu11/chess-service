package com.github.patu11.chessservice.config;

import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.role.Role;
import com.github.patu11.chessservice.user.User;
import com.github.patu11.chessservice.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@Service
public class MyUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User u = this.userRepository.findById(username).orElseThrow(() -> new NotFoundException("User not found."));
//		UserDetails userDetails = org.springframework.security.core.userdetails.User
//				.withUsername(u.getEmail())
//				.password(u.getPassword())
//				.accountExpired(false)
//				.accountLocked(false)
//				.credentialsExpired(false)
//				.disabled(false)
//				.authorities(getAuthorities(u)).build();
//		return userDetails;
		return new MyUserDetails(u);
	}

//	private Collection<GrantedAuthority> getAuthorities(User user) {
//		Set<Role> userRoles = user.getRoles();
//		Collection<GrantedAuthority> authorities = new ArrayList<>(userRoles.size());
//		for (Role userRole : userRoles) {
//			authorities.add(new SimpleGrantedAuthority(userRole.getName().toUpperCase()));
//		}
//		return authorities;
//	}
}
