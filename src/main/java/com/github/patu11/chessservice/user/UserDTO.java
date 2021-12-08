package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.comment.CommentDTO;
import com.github.patu11.chessservice.friend.FriendDTO;
import com.github.patu11.chessservice.game.GameDTO;
import com.github.patu11.chessservice.role.RoleDTO;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String email;
	private String username;
	private String password;
	private List<CommentDTO> comments;
	private List<FriendDTO> friends;
	private Set<RoleDTO> roles;
	private List<GameDTO> games;

	public UserDTO(User user) {
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.comments = user.getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
		this.friends = user.getFriends().stream().map(FriendDTO::new).collect(Collectors.toList());
		this.roles = user.getRoles().stream().map(RoleDTO::new).collect(Collectors.toSet());
		this.games = user.getGames().stream().map(GameDTO::new).collect(Collectors.toList());
	}
}
