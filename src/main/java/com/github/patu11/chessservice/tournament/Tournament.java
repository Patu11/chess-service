package com.github.patu11.chessservice.tournament;

import com.github.patu11.chessservice.comment.Comment;
import com.github.patu11.chessservice.game.Game;
import com.github.patu11.chessservice.role.Role;
import com.github.patu11.chessservice.user.User;
import lombok.*;
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

	@OneToMany(mappedBy = "tournament", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Game> games = new ArrayList<>();

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
}
