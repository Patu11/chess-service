package com.github.patu11.chessservice.friend;

import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.user.User;
import com.github.patu11.chessservice.user.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Service
public class FriendService {
	private final FriendRepository friendRepository;
	private final UserService userService;

	@Autowired
	public FriendService(FriendRepository friendRepository, UserService userService) {
		this.friendRepository = friendRepository;
		this.userService = userService;
	}

	public FriendDTO getFriend(Long id) {
		Friend friend = this.friendRepository.findById(id).orElseThrow(() -> new NotFoundException("Friendship not found"));

		return new FriendDTO(friend);
	}

	public void createFriend(FriendDTO friend) {
		User user1 = this.userService.getRawUser(friend.getUser1());
		User user2 = this.userService.getRawUser(friend.getUser2());
		boolean status = friend.isStatus();

		Friend friend1 = new Friend();
		friend1.setStatus(status);
		friend1.setUser1(user1);
		friend1.setUser2(user2);

		Friend friend2 = new Friend();
		friend2.setStatus(status);
		friend2.setUser1(user2);
		friend2.setUser2(user1);
		
		this.friendRepository.saveAll(List.of(friend1, friend2));
	}
}
