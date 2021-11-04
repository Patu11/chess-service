package com.github.patu11.chessservice.user;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@ToString
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
}
