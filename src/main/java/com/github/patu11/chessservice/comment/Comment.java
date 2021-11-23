package com.github.patu11.chessservice.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.patu11.chessservice.user.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id", nullable = false)
	private Long commentId;

	@Column
	private String content;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;
}
