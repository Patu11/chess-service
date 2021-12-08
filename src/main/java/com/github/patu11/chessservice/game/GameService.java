package com.github.patu11.chessservice.game;

import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.user.User;
import com.github.patu11.chessservice.user.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Log4j
@Service
public class GameService {

	private final GameRepository gameRepository;
	private final UserService userService;

	@Autowired
	public GameService(GameRepository gameRepository, UserService userService) {
		this.gameRepository = gameRepository;
		this.userService = userService;
	}

	public void updateGameState(String code, String state) {
		Game game = this.gameRepository.findById(code).orElseThrow(() -> new NotFoundException("Game not found."));
		game.setState(state);
		this.gameRepository.save(game);
	}

	public void updateGameTurn(String code, String username) {
		Game game = this.gameRepository.findById(code).orElseThrow(() -> new NotFoundException("Game not found."));
		game.setCurrentTurn(username);
		this.gameRepository.save(game);
	}

	public GameDTO getGameByHostOrUser(String host, String user) {
		User user1 = this.userService.getRawUserByUsername(host);
		User user2 = this.userService.getRawUserByUsername(user);

		Optional<Game> optionalGame = this.gameRepository.findGameByHostOrPlayer(user1, user2);
		if (optionalGame.isEmpty()) {
			throw new NotFoundException("Game not found.");
		}
		Game g = optionalGame.get();

		if (g.isEnded()) {
			throw new NotFoundException("Game not found.");
		}

//		if (!g.isAccepted()) {
//			throw new NotFoundException("Game not found.");
//		}

		return new GameDTO(g);
	}
}
