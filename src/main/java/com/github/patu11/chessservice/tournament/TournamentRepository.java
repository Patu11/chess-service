package com.github.patu11.chessservice.tournament;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
