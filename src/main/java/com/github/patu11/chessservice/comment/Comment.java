package com.github.patu11.chessservice.comment;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.github.patu11.chessservice.profile.Profile;
import com.github.patu11.chessservice.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@Table(name = "comments", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
	@Id
	@GenericGenerator(name = "commentgenerator", strategy = "increment")
	@GeneratedValue(generator = "commentgenerator")
	@Column(name = "comment_id", nullable = false)
	private Long commentId;

	@Column
	private String content;

	@Column
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate createdAt;

	@ManyToOne(fetch = FetchType.LAZY)
	private User author;

	@ManyToOne(fetch = FetchType.LAZY)
	private Profile profile;
}
