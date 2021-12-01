package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.exceptions.BadRequestDataException;
import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.exceptions.UserAlreadyExistException;
import com.github.patu11.chessservice.profile.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void createUser(UserDTO user) {
		Optional<User> userOpt = userRepository.findById(user.getEmail());
		if (userOpt.isPresent()) {
			throw new UserAlreadyExistException("User already exist.");
		}
		
		User us = new User();
		us.setUsername(user.getUsername());
		us.setEmail(user.getEmail());
		us.setPassword(user.getPassword());
		us.setComments(new ArrayList<>());

		Profile p = new Profile();
		p.setComments(new ArrayList<>());

		us.setProfile(p);
//		if (user.getEmail().isBlank() || user.getUsername().isBlank() || user.getPassword().isBlank()) {
//			System.out.println("ERROR");
//			throw new BadRequestDataException("Empty data fields.");
//		}
		this.userRepository.save(us);
	}

	public List<UserDTO> getAllUsers() {
		return this.userRepository.findAll().stream().map(UserDTO::new).collect(Collectors.toList());
	}

	public User getRawUser(String username) {
		return this.userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));
	}

	public UserDTO getUser(String username) {
		User u = this.userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));
		return new UserDTO(u);
	}

}
