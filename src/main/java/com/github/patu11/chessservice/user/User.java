package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.game.Game;
import com.github.patu11.chessservice.role.Role;
import com.github.patu11.chessservice.comment.Comment;
import com.github.patu11.chessservice.friend.Friend;
import com.github.patu11.chessservice.profile.Profile;
import com.github.patu11.chessservice.tournament.Tournament;
import lombok.*;

import javax.persistence.*;
import java.util.*;

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

	@OneToMany(mappedBy = "host", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Game> games = new ArrayList<>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
			name = "users_roles",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
	)
	private Set<Role> roles = new HashSet<>();

	@ManyToMany(mappedBy = "users")
	private Set<Tournament> tournaments = new HashSet<>();

	public void addComment(Comment comment) {
		comment.setAuthor(this);
		this.comments.add(comment);
	}

	public void setProfile(Profile profile) {
		profile.setUser(this);
		this.profile = profile;
	}


	public void addRole(Role role) {
		role.addUser(this);
		this.roles.add(role);
	}

	public void removeRole(Role role) {
		role.removeUser(this);
		this.roles.remove(role);
	}

	public void addTournament(Tournament tournament) {
		this.tournaments.add(tournament);
	}

	public void removeTournament(Tournament tournament) {
		this.tournaments.remove(tournament);
	}

	public void addFriend(Friend friend) {
		this.friends.add(friend);
	}
}
