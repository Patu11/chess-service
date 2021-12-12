package com.github.patu11.chessservice.tournament;

import com.github.patu11.chessservice.game.GameDTO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j
@RestController
@RequestMapping("/tournaments")
public class TournamentController {

	private final TournamentService tournamentService;

	@Autowired
	public TournamentController(TournamentService tournamentService) {
		this.tournamentService = tournamentService;
	}

	@PostMapping("/create")
	public void createTournament(@RequestBody TournamentDTO tournament) {
		this.tournamentService.createTournament(tournament);
	}

	@DeleteMapping("/delete/{tournamentId}")
	public void deleteTournament(@PathVariable Long tournamentId) {
		this.tournamentService.deleteTournament(tournamentId);
	}

	@PutMapping("/leave/{tournamentId}")
	public int removeUserFromTournament(@PathVariable Long tournamentId, @RequestBody Map<String, String> data) {
		return this.tournamentService.removeUserFromTournament(tournamentId, data);
	}

	@PutMapping("/join/{tournamentId}")
	public int addUserToTournament(@PathVariable Long tournamentId, @RequestBody Map<String, String> data) {
		return this.tournamentService.addUserToTournament(tournamentId, data);
	}

	@GetMapping("/all")
	public List<TournamentDTO> getAll() {
		return this.tournamentService.getAll();
	}

	@GetMapping("/{tournamentId}")
	public TournamentDTO getTournament(@PathVariable Long tournamentId) {
		return this.tournamentService.getTournament(tournamentId);
	}

	@GetMapping("/games/{tournamentId}")
	public List<GameDTO> getAllTournamentGames(@PathVariable Long tournamentId) {
		return this.tournamentService.getAllTournamentGames(tournamentId);
	}
}
