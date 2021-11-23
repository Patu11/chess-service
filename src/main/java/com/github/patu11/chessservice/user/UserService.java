package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.exceptions.BadRequestDataException;
import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.exceptions.UserAlreadyExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

	private final UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Transactional
	public void createUser(User user) {
		if (userRepository.findById(user.getEmail()).isPresent()) {
			throw new UserAlreadyExistException("User already exist.");
		}

//		if (user.getEmail().isBlank() || user.getUsername().isBlank() || user.getPassword().isBlank()) {
//			System.out.println("ERROR");
//			throw new BadRequestDataException("Empty data fields.");
//		}

		this.userRepository.save(user);
	}

	public UserDTO getUser(String username) {
		User u = this.userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("User not found."));
		return new UserDTO(u);
	}

}
