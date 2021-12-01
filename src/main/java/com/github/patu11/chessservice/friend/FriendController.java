package com.github.patu11.chessservice.friend;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@RequestMapping("/friends")
public class FriendController {
	private final FriendService friendService;

	@Autowired
	public FriendController(FriendService friendService) {
		this.friendService = friendService;
	}

	@GetMapping
	public FriendDTO getFriend(@RequestHeader("friendId") Long id) {
		return this.friendService.getFriend(id);
	}

	@PostMapping()
	public void createFriend(@RequestBody FriendDTO friendDTO) {
		this.friendService.createFriend(friendDTO);
	}
}
