package com.github.patu11.chessservice.user;

import com.github.patu11.chessservice.Role;
import com.github.patu11.chessservice.comment.Comment;
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

	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comment> comments = new ArrayList<>();

}
