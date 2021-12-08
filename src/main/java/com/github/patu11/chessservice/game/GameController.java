package com.github.patu11.chessservice.game;

import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@RequestMapping("/games")
public class GameController {

	private final GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping("/{host}/{user}")
	public GameDTO getGameByHostOrUser(@PathVariable String host, @PathVariable String user) {
		return this.gameService.getGameByHostOrUser(host, user);
	}

	@PutMapping("/status/{code}")
	public void updateGameState(@PathVariable String code, @RequestBody String state) {
		this.gameService.updateGameState(code, state);
	}

	@PutMapping("/turn/{code}")
	public void updateGameTurn(@PathVariable String code, @RequestBody String username) {
		this.gameService.updateGameTurn(code, username);
	}

}
