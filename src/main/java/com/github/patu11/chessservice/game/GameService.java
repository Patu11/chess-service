package com.github.patu11.chessservice.game;

import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.tournament.Tournament;
import com.github.patu11.chessservice.tournament.TournamentRepository;
import com.github.patu11.chessservice.tournament.TournamentService;
import com.github.patu11.chessservice.user.User;
import com.github.patu11.chessservice.user.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

	public Game getRawGameByCode(String code) {
		return this.gameRepository.findById(code).orElseThrow(() -> new NotFoundException("Game not found."));
	}

	public void deleteByCode(String code) {
		this.gameRepository.delete(this.getRawGameByCode(code));
	}

	public void acceptGameInvite(String code) {
		Game g = this.getRawGameByCode(code);
		g.setAccepted(true);
		this.gameRepository.save(g);
	}

	public void updateWinner(String code, Map<String, String> state) {
		Game g = this.getRawGameByCode(code);
		g.setWinner(state.get("winner"));
		g.setEnded(true);
		g.setStarted(false);
		this.gameRepository.save(g);
	}

	public void updateGame(String code, Map<String, String> state) {
		Game game = this.gameRepository.findById(code).orElseThrow(() -> new NotFoundException("Game not found."));
		game.setCurrentTurn(state.get("currentTurn"));
		game.setState(state.get("state"));
		this.gameRepository.save(game);
	}

	public List<GameDTO> getAllGames() {
		return this.gameRepository.findAll().stream().map(GameDTO::new).collect(Collectors.toList());
	}

	public List<Game> getAllGamesRaw() {
		return this.gameRepository.findAll();
	}

	public List<String> getAllCodes() {
		List<Game> games = this.gameRepository.findAll();
		return games.stream().map(Game::getGameCode).collect(Collectors.toList());
	}

	public void createGame(GameDTO game) {
		User host = this.userService.getRawUserByUsername(game.getHost());
		User player = this.userService.getRawUserByUsername(game.getPlayer());
//		Tournament tournament = null;
//		if (game.getTournamentId() != null) {
//			tournament = this.tournamentRepository.findById(game.getTournamentId()).orElseThrow(() -> new NotFoundException("Tournament not found."));
//		}


		Game g = new Game();
		g.setGameCode(game.getCode());
		g.setStarted(game.isStarted());
		g.setAccepted(game.isAccepted());
		g.setEnded(g.isEnded());
		g.setState(game.getState());
		g.setWinner(g.getWinner());
		g.setCurrentTurn(host.getUsername());
		g.setHost(host);
		g.setPlayer(player);
//		g.setTournament(tournament);

		this.gameRepository.save(g);
	}

	public GameDTO getGameByHostOrUser(String username) {

		List<Game> availableGames = this.getAllGamesRaw()
				.stream()
				.filter(g -> g.getHost().getUsername().equals(username) || g.getPlayer().getUsername().equals(username))
				.filter(g -> !g.isEnded() && g.isAccepted())
				.collect(Collectors.toList());


		Optional<Game> optionalGame = availableGames.stream().findFirst();
		if (optionalGame.isEmpty()) {
			throw new NotFoundException("Game not found.");
		}
		Game g = optionalGame.get();
		return new GameDTO(g);
	}
}
