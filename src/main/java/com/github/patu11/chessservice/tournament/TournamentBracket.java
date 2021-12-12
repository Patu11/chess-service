package com.github.patu11.chessservice.tournament;

import com.github.patu11.chessservice.user.User;
import com.github.patu11.chessservice.user.UserDTO;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.util.Pair;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class TournamentBracket {
	private List<String> firstRound;
	private List<String> secondRound;
	private List<String> thirdRound;

//	public TournamentBracket(List<Pair<User, User>> firstRound, List<Pair<User, User>> secondRound, List<Pair<User, User>> thirdRound) {
//		this.firstRound = firstRound.stream().map(e -> Pair.of(new UserDTO(e.getFirst()), new UserDTO(e.getSecond()))).collect(Collectors.toList());
//		this.secondRound = secondRound.stream().map(e -> Pair.of(new UserDTO(e.getFirst()), new UserDTO(e.getSecond()))).collect(Collectors.toList());
//		this.thirdRound = thirdRound.stream().map(e -> Pair.of(new UserDTO(e.getFirst()), new UserDTO(e.getSecond()))).collect(Collectors.toList());
//	}
}
