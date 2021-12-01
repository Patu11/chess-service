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

	@GetMapping("/username/{username}")
	public UserDTO getUserByUsername(@PathVariable("username") String username) {
		return this.userService.getUserByUsername(username);
	}

	@GetMapping("/email/{email}")
	public UserDTO getUserByEmail(@PathVariable("email") String email) {
		return this.userService.getUserByEmail(email);
	}

	@GetMapping("/login")
	public void login() {
	}

	@GetMapping("/all")
	public List<UserDTO> getAllUsers() {
		return this.userService.getAllUsers();
	}
}
