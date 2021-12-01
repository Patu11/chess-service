package com.github.patu11.chessservice.config;

import com.github.patu11.chessservice.role.Role;
import com.github.patu11.chessservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimpleToken {
	private String email;
	private String username;
	private String password;
	private Set<String> roles;

	public SimpleToken(User user) {
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
	}

	@Override
	public String toString() {
		String roles = "[";
		roles += String.join(",", this.roles);
		roles += "]";
		return this.email + ":" + this.password + ":" + this.password + ":" + roles;
	}
}
