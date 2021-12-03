package com.github.patu11.chessservice.friend;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Log4j
@RestController
@RequestMapping("/friends")
public class FriendController {
	private final FriendService friendService;

	@Autowired
	public FriendController(FriendService friendService) {
		this.friendService = friendService;
	}

//	@GetMapping
//	public FriendDTO getFriend(@RequestHeader("friendId") Long id) {
//		return this.friendService.getFriend(id);
//	}

	@GetMapping("/{user1}/{user2}")
	public Map<String, Boolean> friendshipExists(@PathVariable String user1, @PathVariable String user2) {
		Map<String, Boolean> map = new HashMap<>();
		map.put("friendshipExists", this.friendService.friendshipExists(user1, user2));
		return map;
	}

	@PostMapping()
	public void createFriend(@RequestBody FriendDTO friendDTO) {
		this.friendService.createFriend(friendDTO);
	}

	@PutMapping("/accept")
	public void acceptFriendShip(@RequestBody FriendDTO friendDTO) {
		this.friendService.acceptFriendship(friendDTO);
	}

	@DeleteMapping("/delete/{user1}/{user2}")
	public void declineFriendship(@PathVariable String user1, @PathVariable String user2) {
		this.friendService.declineFriendship(user1, user2);
	}
}
