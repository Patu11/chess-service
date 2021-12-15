package com.github.patu11.chessservice.round;

import com.github.patu11.chessservice.game.Game;
import com.github.patu11.chessservice.tournament.Tournament;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "rounds", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Round {
	@javax.persistence.Id
	@GenericGenerator(name = "roundgenerator", strategy = "increment")
	@GeneratedValue(generator = "roundgenerator")
	@Column(name = "round_id", nullable = false)
	private Long id;

	@Column
	private int roundNumber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tournament_id")
	private Tournament tournament;

	@Fetch(FetchMode.SELECT)
	@OneToMany(mappedBy = "round", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Game> games = new ArrayList<>();
}
