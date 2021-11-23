package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.comment.CommentDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private String email;
	private String username;
	private String password;
	private List<CommentDTO> comments;

	public UserDTO(User user) {
		this.email = user.getEmail();
		this.username = user.getUsername();
		this.password = user.getPassword();
		this.comments = user.getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
	}
}
