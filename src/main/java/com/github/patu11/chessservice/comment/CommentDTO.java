package com.github.patu11.chessservice.comment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
	private Long commentId;
	private String content;
	private String createdAt;
	private String author;

	public CommentDTO(Comment comment) {
		this.commentId = comment.getCommentId();
		this.content = comment.getContent();
		this.createdAt = comment.getCreatedAt().toString();
		this.author = comment.getUser().getUsername();
	}
}
