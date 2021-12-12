package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.config.SimpleToken;
import com.github.patu11.chessservice.role.RoleDTO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@Log4j
@RestController
@RequestMapping("/users")
public class UserController {

	private final UserService userService;

	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}

	@PutMapping("/editpassword/{username}")
	public void updateUserPassword(@PathVariable String username, @RequestBody ChangePasswordData password) {
		this.userService.updateUserPassword(username, password);
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

	@GetMapping("/role/{email}")
	public Set<RoleDTO> getUserRole(@PathVariable("email") String email) {
		return this.userService.getUserByEmail(email).getRoles();
	}

	@GetMapping("/login")
	public SimpleToken login(@RequestHeader("authorization") String header) {
		return this.userService.handleLogin(header);
	}

	@GetMapping("/all")
	public List<UserDTO> getAllUsers() {
		return this.userService.getAllUsers();
	}

	@DeleteMapping("/delete/{email}")
	public void deleteUserByEmail(@PathVariable String email) {
		this.userService.deleteUserByEmail(email);
	}
}
