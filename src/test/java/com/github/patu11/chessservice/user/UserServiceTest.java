package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.config.SimpleToken;
import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.friend.FriendRepository;
import com.github.patu11.chessservice.profile.Profile;
import com.github.patu11.chessservice.role.RoleService;
import com.github.patu11.chessservice.tournament.Tournament;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock
	UserRepository userRepository;

	@Mock
	RoleService roleService;
	@Mock
	PasswordEncoder passwordEncoder;
	@Mock
	FriendRepository friendRepository;


	@InjectMocks
	UserService userService;

	@Test
	public void login_should_return_token() {
		when(userRepository.findById("test@test.com")).thenReturn(
				Optional.of(new User("test@test.com", "test", "testPassword", new Profile(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new HashSet<>(), new HashSet<>())
				));

		SimpleToken token = userService.handleLogin("Basic dGVzdEB0ZXN0LmNvbTp0ZXN0UGFzc3dvcmQ6dGVzdA==");
		assertEquals("dGVzdEB0ZXN0LmNvbTp0ZXN0UGFzc3dvcmQ6dGVzdDo=", token.getToken());

	}

	@Test
	public void get_user_by_email_should_throw_exception() {
		Exception exception = assertThrows(NotFoundException.class, () -> {
			userService.getRawUserByEmail("");
		});

		String expectedMessage = "User not found.";
		String actualMessage = exception.getMessage();

		assertTrue(actualMessage.contains(expectedMessage));
	}

	@Test
	public void get_all_should_return_all_users() {
		when(userRepository.findAll()).thenReturn(Arrays.asList(
				new User("test1@test.com", "test1", "testPassword1", new Profile(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new HashSet<>(), new HashSet<>()),
				new User("test2@test.com", "test2", "testPassword2", new Profile(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new HashSet<>(), new HashSet<>()),
				new User("test3@test.com", "test3", "testPassword3", new Profile(), new LinkedList<>(), new LinkedList<>(), new LinkedList<>(), new HashSet<>(), new HashSet<>())
		));

		List<UserDTO> users = userService.getAllUsers();

		assertEquals("test1@test.com", users.get(0).getEmail());
		assertEquals("test2@test.com", users.get(1).getEmail());
		assertEquals("test3@test.com", users.get(2).getEmail());

	}
}