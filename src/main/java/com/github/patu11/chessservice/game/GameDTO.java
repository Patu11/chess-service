package com.github.patu11.chessservice.game;

import com.github.patu11.chessservice.tournament.Tournament;
import com.github.patu11.chessservice.tournament.TournamentDTO;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
	private String code;
	private String host;
	private String player;
	private String state;
	private String winner;
	private String currentTurn;
	private Long tournamentId;
	private boolean started;
	private boolean accepted;
	private boolean ended;

	public GameDTO(Game game) {
		this.code = game.getGameCode();
		this.host = game.getHost().getUsername();
//		this.player = game.getPlayer().getUsername();
		this.player = game.getPlayer() != null ? game.getPlayer().getUsername() : "";
		this.state = game.getState();
		this.winner = game.getWinner();
		this.currentTurn = game.getCurrentTurn();
		this.tournamentId = game.getTournament() == null ? -1L : game.getTournament().getTournamentId();
		this.started = game.isStarted();
		this.accepted = game.isAccepted();
		this.ended = game.isEnded();
	}
}
