package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.Role;
import com.github.patu11.chessservice.comment.Comment;
import com.github.patu11.chessservice.friend.Friend;
import com.github.patu11.chessservice.profile.Profile;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@Column
	private String email;

	@Column
	private String username;

	@Column
	private String password;

	@OneToOne(fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "user")
	private Profile profile;

	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

	@OneToMany(mappedBy = "user1", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Friend> friends = new ArrayList<>();

	public void addComment(Comment comment) {
		comment.setAuthor(this);
		this.comments.add(comment);
	}

	public void setProfile(Profile profile) {
		profile.setUser(this);
		this.profile = profile;
	}
}
