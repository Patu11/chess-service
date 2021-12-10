package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.config.SimpleToken;
import com.github.patu11.chessservice.exceptions.BadRequestDataException;
import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.exceptions.UserAlreadyExistException;
import com.github.patu11.chessservice.friend.Friend;
import com.github.patu11.chessservice.friend.FriendRepository;
import com.github.patu11.chessservice.friend.FriendService;
import com.github.patu11.chessservice.profile.Profile;
import com.github.patu11.chessservice.role.Role;
import com.github.patu11.chessservice.role.RoleService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Log4j
@Service
public class UserService {

	private final UserRepository userRepository;
	private final RoleService roleService;
	private final PasswordEncoder passwordEncoder;
	private final FriendRepository friendRepository;

	@Autowired
	public UserService(UserRepository userRepository, RoleService roleService, PasswordEncoder passwordEncoder, FriendRepository friendRepository) {
		this.userRepository = userRepository;
		this.roleService = roleService;
		this.passwordEncoder = passwordEncoder;
		this.friendRepository = friendRepository;
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
		us.setPassword(this.passwordEncoder.encode(user.getPassword()));
		us.setComments(new ArrayList<>());
		us.addRole(role);

		Profile p = new Profile();
		p.setComments(new ArrayList<>());

		us.setProfile(p);

		this.userRepository.save(us);
	}

	public void updateUserPassword(String username, ChangePasswordData password) {
		if (username.isBlank() || password.getPassword().isBlank()) {
			throw new BadRequestDataException("Username or password is empty.");
		}

		User u = this.getRawUserByUsername(username);
		u.setPassword(this.passwordEncoder.encode(password.getPassword()));
		this.userRepository.save(u);
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

	public void deleteUserByEmail(String email) {
		if (email.isBlank()) {
			throw new BadRequestDataException("Email is empty.");
		}

		User u = this.getRawUserByEmail(email);
		List<Friend> friends = this.friendRepository.findAllByUser1(u);
		friends.addAll(this.friendRepository.findAllByUser2(u));

		this.friendRepository.deleteAll(friends);
		this.userRepository.deleteById(email);
	}

	public SimpleToken handleLogin(String header) {
		if (header.isBlank()) {
			throw new BadRequestDataException("Authorization token is empty.");
		}
		byte[] decodedBytes = Base64.getDecoder().decode(header.substring(6));
		String decodedString = new String(decodedBytes);
		String email = decodedString.split(":")[0];
		User u = this.getRawUserByEmail(email);
		return new SimpleToken(u);
	}
}
