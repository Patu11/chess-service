package com.github.patu11.chessservice.tournament;

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

	@PostMapping
	public void createTournament(@RequestBody TournamentDTO tournament) {
		this.tournamentService.createTournament(tournament);
	}

	@PutMapping("/join/{tournamentId}")
	public void addUserToTournament(@PathVariable Long tournamentId, @RequestBody Map<String, String> data) {
		log.info(tournamentId + " - " + data.get("username"));
		this.tournamentService.addUserToTournament(tournamentId, data);
	}

	@GetMapping("/all")
	public List<TournamentDTO> getAll() {
		return this.tournamentService.getAll();
	}

	@GetMapping("/{tournamentId}")
	public TournamentDTO getTournament(@PathVariable Long tournamentId) {
		return this.tournamentService.getTournament(tournamentId);
	}
}
