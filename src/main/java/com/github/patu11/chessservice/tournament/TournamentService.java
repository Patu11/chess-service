package com.github.patu11.chessservice.tournament;

import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.user.User;
import com.github.patu11.chessservice.user.UserDTO;
import com.github.patu11.chessservice.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TournamentService {

	private final TournamentRepository tournamentRepository;
	private final UserService userService;

	@Autowired
	public TournamentService(TournamentRepository tournamentRepository, UserService userService) {
		this.tournamentRepository = tournamentRepository;
		this.userService = userService;
	}

	public void createTournament(TournamentDTO tournament) {
		Tournament t = new Tournament();
		t.setTitle(tournament.getTitle());
		t.setMaxPlayers(tournament.getMaxPlayers());
		t.setStartDate(LocalDate.parse(tournament.getStartDate()));
		t.setEndDate(LocalDate.parse(tournament.getEndDate()));

		this.tournamentRepository.save(t);
	}

	public void addUserToTournament(Long tournamentId, Map<String, String> data) {
		User user = this.userService.getRawUserByUsername(data.get("username"));
		Tournament t = this.getTournamentRaw(tournamentId);
		t.addUser(user);
		this.tournamentRepository.save(t);
	}

	public TournamentDTO getTournament(Long tournamentId) {
		return new TournamentDTO(this.getTournamentRaw(tournamentId));
	}

	public Tournament getTournamentRaw(Long tournamentId) {
		return this.tournamentRepository.findById(tournamentId).orElseThrow(() -> new NotFoundException("Tournament not found."));
	}

	public List<TournamentDTO> getAll() {
		return this.tournamentRepository.findAll().stream().map(TournamentDTO::new).collect(Collectors.toList());
	}
}
