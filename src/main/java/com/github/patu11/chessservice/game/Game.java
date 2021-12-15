package com.github.patu11.chessservice.game;

import com.github.patu11.chessservice.round.Round;
import com.github.patu11.chessservice.tournament.Tournament;
import com.github.patu11.chessservice.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "games", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Game {

	@Id
	@Column
	private String gameCode;

	@Column
	private boolean started;

	@Column
	private boolean accepted;

	@Column
	private boolean ended;

	@Column
	private String state;

	@Column
	private String winner;

	@Column
	private String currentTurn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "round_id")
	private Round round;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tournament_id")
	private Tournament tournament;

	@ManyToOne
	@JoinColumn(name = "host")
	private User host;

	@ManyToOne
	@JoinColumn(name = "player")
	private User player;

}
