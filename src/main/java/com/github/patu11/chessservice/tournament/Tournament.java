package com.github.patu11.chessservice.tournament;

import com.github.patu11.chessservice.comment.Comment;
import com.github.patu11.chessservice.game.Game;
import com.github.patu11.chessservice.role.Role;
import com.github.patu11.chessservice.round.Round;
import com.github.patu11.chessservice.user.User;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@Table(name = "tournaments", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Tournament {

	@Id
	@GenericGenerator(name = "tournamentgenerator", strategy = "increment")
	@GeneratedValue(generator = "tournamentgenerator")
	@Column(name = "tournament_id", nullable = false)
	private Long tournamentId;

	@Column
	private String title;
	
	@Column
	private int maxPlayers;

	@Column
	private LocalDate startDate;

	@Column
	private LocalDate endDate;

	@Column
	private String winner;

	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "tournament", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Game> games = new ArrayList<>();

	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "tournament", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Round> rounds = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_tournaments",
			joinColumns = @JoinColumn(name = "tournament_id"),
			inverseJoinColumns = @JoinColumn(name = "user_id")
	)
	private Set<User> users = new HashSet<>();

	public void addUser(User user) {
		user.addTournament(this);
		this.users.add(user);
	}

	public void removeUser(User user) {
		user.removeTournament(this);
		this.users.remove(user);
	}

	public void addRound(Round round) {
		round.setTournament(this);
		this.rounds.add(round);
	}

	public void removeRound(Round round) {
		round.setTournament(null);
		this.rounds.remove(round);
	}

	public void addGame(Game game) {
		game.setTournament(this);
		this.games.add(game);
	}

	public void removeGame(Game game) {
		game.setTournament(null);
		this.games.remove(game);
	}
}
