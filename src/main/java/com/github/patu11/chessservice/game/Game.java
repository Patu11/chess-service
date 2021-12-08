package com.github.patu11.chessservice.game;

import com.github.patu11.chessservice.user.User;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
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

	@ManyToOne
	@JoinColumn(name = "host")
	private User host;

	@ManyToOne
	@JoinColumn(name = "player")
	private User player;

}
