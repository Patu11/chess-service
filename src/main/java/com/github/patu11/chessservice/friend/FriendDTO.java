package com.github.patu11.chessservice.friend;

import com.github.patu11.chessservice.user.UserDTO;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class FriendDTO {

	private String sender;
	private String user1;
	private String user2;
	private boolean status;

	public FriendDTO(Friend friend) {
		this.sender = friend.getSender();
		this.user1 = friend.getUser1().getUsername();
		this.user2 = friend.getUser2().getUsername();
		this.status = friend.isStatus();
	}
}
