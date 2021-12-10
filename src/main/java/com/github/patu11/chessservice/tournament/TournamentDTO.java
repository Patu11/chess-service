package com.github.patu11.chessservice.tournament;

import com.github.patu11.chessservice.game.GameDTO;
import com.github.patu11.chessservice.user.UserDTO;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class TournamentDTO {
	private Long tournamentId;
	private String title;
	private int maxPlayers;
	private String startDate;
	private String endDate;
	private Set<UserDTO> users;
	private List<GameDTO> games;

	public TournamentDTO(Tournament tournament) {
		this.tournamentId = tournament.getTournamentId();
		this.title = tournament.getTitle();
		this.maxPlayers = tournament.getMaxPlayers();
		this.startDate = tournament.getStartDate().toString();
		this.endDate = tournament.getEndDate().toString();
		this.users = tournament.getUsers().stream().map(UserDTO::new).collect(Collectors.toSet());
		this.games = tournament.getGames().stream().map(GameDTO::new).collect(Collectors.toList());
	}
}
