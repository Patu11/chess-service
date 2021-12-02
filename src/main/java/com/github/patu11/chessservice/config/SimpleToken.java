package com.github.patu11.chessservice.config;

import com.github.patu11.chessservice.role.Role;
import com.github.patu11.chessservice.user.User;
import lombok.Getter;

import java.util.Base64;
import java.util.stream.Collectors;

@Getter
public class SimpleToken {
	private final String token;

	public SimpleToken(User user) {
		String roles = user.getRoles().stream().map(Role::getName).collect(Collectors.joining(","));
		String tempToken = user.getEmail() + ":" + user.getPassword() + ":" + user.getUsername() + ":" + roles;
		this.token = Base64.getEncoder().encodeToString(tempToken.getBytes());
	}
}
