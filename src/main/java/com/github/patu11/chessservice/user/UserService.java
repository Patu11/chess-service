package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.config.SimpleToken;
import com.github.patu11.chessservice.exceptions.BadRequestDataException;
import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.exceptions.UserAlreadyExistException;
import com.github.patu11.chessservice.profile.Profile;
import com.github.patu11.chessservice.role.Role;
import com.github.patu11.chessservice.role.RoleService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Log4j
@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
	}

	@Transactional
	public void createUser(UserDTO user) {
		Optional<User> userOpt = userRepository.findById(user.getEmail());
		if (userOpt.isPresent()) {
			throw new UserAlreadyExistException("User already exist.");
		}

		User us = new User();
		Role role = this.roleService.getRole("USER");
		us.setUsername(user.getUsername());
		us.setEmail(user.getEmail());
		log.info("Password: " + user.getPassword());
		us.setPassword(this.passwordEncoder.encode(user.getPassword()));
		us.setComments(new ArrayList<>());
		us.addRole(role);

		Profile p = new Profile();
		p.setComments(new ArrayList<>());

		us.setProfile(p);

		this.userRepository.save(us);
	}

	public List<UserDTO> getAllUsers() {
		return this.userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
	}

	public User getRawUserByUsername(String username) {
		return this.userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));
	}

	public User getRawUserByEmail(String email) {
		return this.userRepository.findById(email).orElseThrow(() -> new NotFoundException("User not found."));
	}

	public UserDTO getUserByUsername(String username) {
		User u = this.userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));
		return new UserDTO(u);
	}

	public UserDTO getUserByEmail(String email) {
		User u = this.userRepository.findById(email).orElseThrow(() -> new NotFoundException("User not found."));
		return new UserDTO(u);
	}

}
