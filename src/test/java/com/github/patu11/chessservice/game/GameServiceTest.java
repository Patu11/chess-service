package com.github.patu11.chessservice.game;

import com.github.patu11.chessservice.profile.Profile;
import com.github.patu11.chessservice.round.Round;
import com.github.patu11.chessservice.tournament.Tournament;
import com.github.patu11.chessservice.user.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

	@Mock
	GameRepository gameRepository;

	@InjectMocks
	GameService gameService;

	@Test
	void should_accept_game_invite() {
		when(gameRepository.findById("as12Qw1")).thenReturn(
				Optional.of(new Game("as12Qw1", false, false, false, "", "",
						"", new Round(), new Tournament(), new User(), new User()))
		);

		gameService.acceptGameInvite("as12Qw1");
		Game g = gameService.getRawGameByCode("as12Qw1");
		assertTrue(g.isAccepted());
	}

	@Test
	void should_return_all_games_codes() {
		when(gameRepository.findAll()).thenReturn(Arrays.asList(
				new Game("as12Qw1", false, false, false, "", "", "",
						new Round(), new Tournament(), new User(), new User()),
				new Game("as12Qw2", false, false, false, "", "", "",
						new Round(), new Tournament(), new User(), new User()),
				new Game("as12Qw3", false, false, false, "", "", "",
						new Round(), new Tournament(), new User(), new User())
		));

		List<String> codes = gameService.getAllCodes();
		assertEquals("as12Qw1", codes.get(0));
		assertEquals("as12Qw2", codes.get(1));
		assertEquals("as12Qw3", codes.get(2));
	}
}