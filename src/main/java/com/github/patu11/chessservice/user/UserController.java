package com.github.patu11.chessservice.user;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Log4j
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PostMapping("/signup")
	public void createUser(@RequestBody UserDTO user) {
		this.userService.createUser(user);
	}

	@GetMapping
	public UserDTO getUser(@RequestHeader("username") String username) {
		return this.userService.getUser(username);
	}

	@GetMapping("/all")
	public List<UserDTO> getAllUsers() {
		return this.userService.getAllUsers();
	}
}
