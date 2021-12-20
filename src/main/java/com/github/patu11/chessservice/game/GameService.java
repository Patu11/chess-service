package com.github.patu11.chessservice.game;

import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.round.Round;
import com.github.patu11.chessservice.tournament.Tournament;
import com.github.patu11.chessservice.tournament.TournamentRepository;
import com.github.patu11.chessservice.tournament.TournamentService;
import com.github.patu11.chessservice.user.User;
import com.github.patu11.chessservice.user.UserService;
import com.github.patu11.chessservice.utils.CodeGenerator;
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
	private final TournamentRepository tournamentRepository;

	@Autowired
	public GameService(GameRepository gameRepository, UserService userService, TournamentRepository tournamentRepository) {
		this.gameRepository = gameRepository;
		this.userService = userService;
		this.tournamentRepository = tournamentRepository;
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
		//-------------
		//check if game is tournament game
		if (g.getTournament() != null) {
			Tournament t = g.getTournament();
			if (g.getRound() != null) {
				//check round number where game was played
				int currentRoundNumber = g.getRound().getRoundNumber();
				//if it is last round then set winner for entire tournament
				if (currentRoundNumber == 3) {
					t.setWinner(state.get("winner"));
				} else {
					//check if game end up with draw, if yes create another game with switched players
					if (state.get("winner").equals("DRAW")) {
						Game newGame = this.createGameEmptyPlayer(g.getPlayer());
						newGame.setPlayer(g.getHost());
						newGame.setTournament(t);
						newGame.setRound(g.getRound());
						this.gameRepository.save(newGame);
					} else {
						//get next round based on previous round number
						Round nextRound = t.getRounds().stream()
								.filter(round -> round.getRoundNumber() == (currentRoundNumber + 1))
								.findFirst()
								.orElseThrow(() -> new NotFoundException("Round not found."));

						Optional<Game> tempGame = nextRound.getGames().stream()
								.filter(temp -> temp.getPlayer() == null).findFirst();
						User winner = this.userService.getRawUserByUsername(state.get("winner"));
						//check if there is game with empty player which means that someone already won game
						if (tempGame.isPresent()) {
							tempGame.get().setPlayer(winner);
							//if no one won game create new and set first player, add new game to round
						} else {
							Game newGame = this.createGameEmptyPlayer(winner);
							newGame.setTournament(t);
							newGame.setRound(nextRound);
							nextRound.getGames().add(newGame);
						}
					}
				}

			}
			this.tournamentRepository.save(t);
		}
		//-------------
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

	public Game createGameEmptyPlayer(User host) {
		Game g = new Game();
		final String state = "rp****PR:np****PN:bp****PB:qp****PQ:kp****PK:bp****PB:np****PN:rp****PR";
		g.setGameCode(CodeGenerator.generateCode());
		g.setStarted(false);
		g.setAccepted(false);
		g.setEnded(false);
		g.setState(state);
		g.setCurrentTurn(host.getUsername());
		g.setHost(host);
		return g;
	}

	public void createGame(GameDTO game) {
		User host = this.userService.getRawUserByUsername(game.getHost());
		User player = this.userService.getRawUserByUsername(game.getPlayer());

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

		this.gameRepository.save(g);
	}

	public void deleteGames(List<Game> games) {
		this.gameRepository.deleteAll(games);
	}

	public GameDTO getGameByHostOrUser(String username) {

		List<Game> availableGames = this.getAllGamesRaw()
				.stream()
				.filter(g -> g.getPlayer() != null && (g.getHost().getUsername().equals(username) || g.getPlayer().getUsername().equals(username)))
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
