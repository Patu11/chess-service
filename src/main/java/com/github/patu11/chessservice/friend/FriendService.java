package com.github.patu11.chessservice.friend;

import com.github.patu11.chessservice.exceptions.BadRequestDataException;
import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.user.User;
import com.github.patu11.chessservice.user.UserService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

	public boolean friendshipExists(String user1, String user2) {
		User us1 = this.userService.getRawUserByUsername(user1);
		User us2 = this.userService.getRawUserByUsername(user2);

		return this.friendRepository.existsByUser1AndUser2(us1, us2);
	}

//	public FriendDTO getFriend(Long id) {
//		Friend friend = this.friendRepository.findById(id).orElseThrow(() -> new NotFoundException("Friendship not found"));
//
//		return new FriendDTO(friend);
//	}

	public void createFriend(FriendDTO friend) {
		User user1 = this.userService.getRawUserByUsername(friend.getUser1());
		User user2 = this.userService.getRawUserByUsername(friend.getUser2());
		boolean status = friend.isStatus();

		Friend friend1 = new Friend();
		friend1.setSender(friend.getSender());
		friend1.setStatus(status);
		friend1.setUser1(user1);
		friend1.setUser2(user2);

		Friend friend2 = new Friend();
		friend2.setSender(friend.getSender());
		friend2.setStatus(status);
		friend2.setUser1(user2);
		friend2.setUser2(user1);

		this.friendRepository.saveAll(List.of(friend1, friend2));
	}

	public void acceptFriendship(FriendDTO friendDTO) {
		User user1 = this.userService.getRawUserByUsername(friendDTO.getUser1());
		User user2 = this.userService.getRawUserByUsername(friendDTO.getUser2());
		Optional<Friend> friend1Optional = this.friendRepository.findFriendByUser1AndUser2(user1, user2);
		Optional<Friend> friend2Optional = this.friendRepository.findFriendByUser1AndUser2(user2, user1);
		Friend friend1 = friend1Optional.orElseThrow(() -> new NotFoundException("Friendship not found."));
		Friend friend2 = friend2Optional.orElseThrow(() -> new NotFoundException("Friendship not found."));

		friend1.setStatus(true);
		friend2.setStatus(true);

		this.friendRepository.saveAll(List.of(friend1, friend2));
	}

	public void deleteAllUserFriendships(String email) {
		User u = this.userService.getRawUserByEmail(email);
		List<Friend> friends = this.friendRepository.findAllByUser1(u);
		friends.addAll(this.friendRepository.findAllByUser2(u));

		this.friendRepository.deleteAll(friends);
	}

	public void declineFriendship(String us1, String us2) {
		User user1 = this.userService.getRawUserByUsername(us1);
		User user2 = this.userService.getRawUserByUsername(us2);
		Optional<Friend> friend1Optional = this.friendRepository.findFriendByUser1AndUser2(user1, user2);
		Optional<Friend> friend2Optional = this.friendRepository.findFriendByUser1AndUser2(user2, user1);
		Friend friend1 = friend1Optional.orElseThrow(() -> new NotFoundException("Friendship not found."));
		Friend friend2 = friend2Optional.orElseThrow(() -> new NotFoundException("Friendship not found."));
		this.friendRepository.deleteAll(List.of(friend1, friend2));
	}
}
