package com.github.patu11.chessservice.tournament;

import java.time.LocalDate;
import java.util.List;

import com.github.patu11.chessservice.game.Game;
import com.github.patu11.chessservice.round.Round;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Log4j
@Component
public class TournamentStarter {

	private final TournamentService tournamentService;

	@Autowired
	public TournamentStarter(TournamentService tournamentService) {
		this.tournamentService = tournamentService;
	}

//	@Scheduled(fixedDelay = 10000)
//	public void handleTournamentStart() {
//		this.tournamentService.handleTournamentStart(this.tournamentService.getTournamentRaw(5L));
//	}

	@Scheduled(cron = "0 1 0 * * ?")
	public void handleTournamentStart() {
		LocalDate now = LocalDate.now();
		List<Tournament> tournaments = this.tournamentService.getAllRaw();
		for (Tournament t : tournaments) {
			if (t.getStartDate().isEqual(now) && t.getMaxPlayers() == t.getUsers().size()) {
				this.tournamentService.handleTournamentStart(t);
			} else if (t.getStartDate().isEqual(now) && t.getMaxPlayers() > t.getUsers().size()) {
				this.tournamentService.deleteTournament(t.getTournamentId());
			}
		}
	}
}
