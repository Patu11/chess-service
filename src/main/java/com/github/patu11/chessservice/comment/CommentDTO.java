package com.github.patu11.chessservice.comment;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	private String content;
	private String createdAt;
	private String author;
	private Long profileId;

	public CommentDTO(Comment comment) {
		this.content = comment.getContent();
		this.createdAt = comment.getCreatedAt().toString();
		this.author = comment.getAuthor().getUsername();
		this.profileId = comment.getProfile().getId();
	}
}
