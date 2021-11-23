package com.github.patu11.chessservice.profile;

import com.github.patu11.chessservice.comment.Comment;
import com.github.patu11.chessservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "profiles", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
	@Id
	@GenericGenerator(name = "profilegenerator", strategy = "increment")
	@GeneratedValue(generator = "profilegenerator")
	@Column(name = "id", nullable = false)
	private Long Id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "user_email", nullable = false)
	private User user;

	@OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	public void addComment(Comment comment) {
		comment.setProfile(this);
		this.comments.add(comment);
	}

	public void setUser(User user) {
		this.user = user;
	}
}
