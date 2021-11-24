package com.github.patu11.chessservice.friend;

import com.github.patu11.chessservice.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendService {
	private final FriendRepository friendRepository;

	@Autowired
	public FriendService(FriendRepository friendRepository) {
		this.friendRepository = friendRepository;
	}

	public FriendDTO getFriend(Long id) {
		Friend friend = this.friendRepository.findById(id).orElseThrow(() -> new NotFoundException("Friendship not found"));

		return new FriendDTO(friend);
	}
}
