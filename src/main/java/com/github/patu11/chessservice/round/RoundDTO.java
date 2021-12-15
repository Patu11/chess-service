package com.github.patu11.chessservice.round;

import com.github.patu11.chessservice.game.GameDTO;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RoundDTO {
	private Long id;
	private int roundNumber;
	private List<GameDTO> games;

	public RoundDTO(Round round) {
		this.id = round.getId();
		this.roundNumber = round.getRoundNumber();
		this.games = round.getGames().stream().map(GameDTO::new).collect(Collectors.toList());
	}
}
