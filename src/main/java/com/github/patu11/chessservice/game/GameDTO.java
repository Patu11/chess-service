package com.github.patu11.chessservice.game;

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
	private boolean started;
	private boolean accepted;
	private boolean ended;

	public GameDTO(Game game) {
		this.code = game.getGameCode();
		this.host = game.getHost().getUsername();
		this.player = game.getPlayer().getUsername();
		this.state = game.getState();
		this.winner = game.getWinner();
		this.currentTurn = game.getCurrentTurn();
		this.started = game.isStarted();
		this.accepted = game.isAccepted();
		this.ended = game.isEnded();
	}
}
