package com.github.patu11.chessservice.game;

import com.github.patu11.chessservice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<Game, String> {
	Optional<Game> findGameByHostOrPlayer(User host, User player);
}
