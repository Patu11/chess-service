package com.github.patu11.chessservice.tournament;

import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.game.Game;
import com.github.patu11.chessservice.game.GameDTO;
import com.github.patu11.chessservice.game.GameService;
import com.github.patu11.chessservice.round.Round;
import com.github.patu11.chessservice.user.User;
import com.github.patu11.chessservice.user.UserDTO;
import com.github.patu11.chessservice.user.UserService;
import com.github.patu11.chessservice.utils.CodeGenerator;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Log4j
@Service
public class TournamentService {

	private final TournamentRepository tournamentRepository;
	private final UserService userService;
	private final GameService gameService;

	public TournamentService(TournamentRepository tournamentRepository, UserService userService, GameService gameService) {
		this.tournamentRepository = tournamentRepository;
		this.userService = userService;
		this.gameService = gameService;
	}

	public void createTournament(TournamentDTO tournament) {
		Tournament t = new Tournament();
		t.setTitle(tournament.getTitle());
		t.setMaxPlayers(tournament.getMaxPlayers());
		t.setStartDate(LocalDate.parse(tournament.getStartDate()));
		t.setEndDate(LocalDate.parse(tournament.getEndDate()));

		this.tournamentRepository.save(t);
	}

	public void deleteTournament(Long tournamentId) {
		Tournament t = this.getTournamentRaw(tournamentId);
		this.tournamentRepository.delete(t);
	}

	public void deleteTournamentGames(Long tournamentId) {
		Tournament t = this.getTournamentRaw(tournamentId);
		this.gameService.deleteGames(t.getGames().stream()
				.filter(g -> !g.isAccepted())
				.collect(Collectors.toList()));
	}


	public int removeUserFromTournament(Long tournamentId, Map<String, String> data) {
		User user = this.userService.getRawUserByUsername(data.get("username"));
		Tournament t = this.getTournamentRaw(tournamentId);

		if (t.getWinner() == null || t.getWinner().isBlank()) {
			Optional<Game> g = t.getGames().stream().filter(gm -> (gm.getPlayer() == user || gm.getHost() == user) && !gm.isEnded()).findFirst();
			if (g.isPresent()) {
				Game game = g.get();
				Map<String, String> winner = new HashMap<>();
				if (game.getHost().getUsername().equals(user.getUsername())) {
					winner.put("winner", game.getPlayer().getUsername());
//					game.setWinner(game.getPlayer().getUsername());
				} else if (game.getPlayer().getUsername().equals(user.getUsername())) {
					winner.put("winner", game.getHost().getUsername());
//					game.setWinner(game.getHost().getUsername());
				}

				this.gameService.updateWinner(game.getGameCode(), winner);
				game.setAccepted(true);
				game.setEnded(true);
				game.setStarted(false);
			}
		}

		t.removeUser(user);
		this.tournamentRepository.save(t);
		return t.getUsers().size();
	}

	public int addUserToTournament(Long tournamentId, Map<String, String> data) {
		User user = this.userService.getRawUserByUsername(data.get("username"));
		Tournament t = this.getTournamentRaw(tournamentId);
		t.addUser(user);
		this.tournamentRepository.save(t);
		return t.getUsers().size();
	}

	public TournamentDTO getTournament(Long tournamentId) {
		Tournament temp = this.getTournamentRaw(tournamentId);
		return new TournamentDTO(temp);
	}

	public Tournament getTournamentRaw(Long tournamentId) {
		return this.tournamentRepository.findById(tournamentId).orElseThrow(() -> new NotFoundException("Tournament not found."));
	}

	public List<GameDTO> getAllTournamentGames(Long tournamentId) {
		return this.gameService.getAllGames().stream()
				.filter(g -> g.getTournamentId().longValue() == tournamentId.longValue())
				.collect(Collectors.toList());
	}

	public List<TournamentDTO> getAll() {
		return this.tournamentRepository.findAll().stream().map(TournamentDTO::new).collect(Collectors.toList());
	}

	public List<Tournament> getAllRaw() {
		return this.tournamentRepository.findAll();
	}

	private List<Pair<User, User>> rollBracket(Tournament tournament) {
		List<Pair<User, User>> roundGames = new ArrayList<>();
		List<User> tempList = new ArrayList<>(tournament.getUsers());
		for (int i = 0; i <= tournament.getMaxPlayers() - 2; i += 2) {
			User u1 = tempList.get(i);
			User u2 = tempList.get(i + 1);
			roundGames.add(Pair.of(u1, u2));
		}
		return roundGames;
	}

	public void handleTournamentStart(Tournament tournament) {
		List<Pair<User, User>> roundGames = this.rollBracket(tournament);

		//Adding games to database
		Round r1 = new Round();
		r1.setRoundNumber(1);
		r1.setTournament(tournament);

		Round r2 = new Round();
		r2.setRoundNumber(2);
		r2.setTournament(tournament);

		Round r3 = new Round();
		r3.setRoundNumber(3);
		r3.setTournament(tournament);
		tournament.setGames(this.generateGames(tournament, roundGames, r1));
		tournament.setRounds(List.of(r1, r2, r3));
		this.tournamentRepository.save(tournament);
	}

	private List<Game> generateGames(Tournament tournament, List<Pair<User, User>> roundGames, Round r) {
		List<String> codes = this.gameService.getAllCodes();
		final String state = "rp****PR:np****PN:bp****PB:qp****PQ:kp****PK:bp****PB:np****PN:rp****PR";
		List<Game> gameList = new ArrayList<>();

		for (Pair<User, User> round : roundGames) {
			String code = CodeGenerator.generateCode();
			while (codes.contains(code)) {
				code = CodeGenerator.generateCode();
			}

			Game g = new Game();
			g.setGameCode(code);
			g.setStarted(false);
			g.setAccepted(false);
			g.setEnded(false);
			g.setState(state);
			g.setWinner("");
			g.setCurrentTurn(round.getFirst().getUsername());
			g.setHost(round.getFirst());
			g.setPlayer(round.getSecond());
			g.setTournament(tournament);
			g.setRound(r);
			gameList.add(g);
		}
		return gameList;
	}
}
