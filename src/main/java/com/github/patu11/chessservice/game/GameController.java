package com.github.patu11.chessservice.game;

import com.github.patu11.chessservice.friend.FriendDTO;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Log4j
@RestController
@RequestMapping("/games")
public class GameController {

	private final GameService gameService;

	public GameController(GameService gameService) {
		this.gameService = gameService;
	}

	@GetMapping("/{username}")
	public GameDTO getGameByHostOrUser(@PathVariable String username) {
		return this.gameService.getGameByHostOrUser(username);
	}

	@GetMapping("/all")
	public List<GameDTO> getAllGames() {
		return this.gameService.getAllGames();
	}

	@GetMapping("/codes")
	public List<String> getAllCodes() {
		return this.gameService.getAllCodes();
	}

	@PostMapping
	public void createGame(@RequestBody GameDTO game) {
		this.gameService.createGame(game);
	}

	@PutMapping("/update/{code}")
	public void updateGame(@PathVariable String code, @RequestBody Map<String, String> state) {
		this.gameService.updateGame(code, state);
	}

	@PutMapping("/winner/{code}")
	public void updateWinner(@PathVariable String code, @RequestBody Map<String, String> state) {
		this.gameService.updateWinner(code, state);
	}

	@PutMapping("/accept/{code}")
	public void acceptGameInvite(@PathVariable String code) {
		this.gameService.acceptGameInvite(code);
	}

	@DeleteMapping("/delete/{code}")
	public void declineFriendship(@PathVariable String code) {
		this.gameService.deleteByCode(code);
	}

}
