package com.github.patu11.chessservice.user;

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

		this.userRepository.save(user);
	}

}
