package com.github.patu11.chessservice.profile;

import com.github.patu11.chessservice.comment.CommentDTO;
import com.github.patu11.chessservice.user.UserDTO;
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
public class ProfileDTO {
	private Long profileId;
	private UserDTO user;
	private List<CommentDTO> comments;

	public ProfileDTO(Profile profile) {
		this.profileId = profile.getId();
		this.user = new UserDTO(profile.getUser());
		this.comments = profile.getComments().stream().map(CommentDTO::new).collect(Collectors.toList());
	}
}
