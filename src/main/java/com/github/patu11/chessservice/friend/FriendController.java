package com.github.patu11.chessservice.friend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
