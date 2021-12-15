package com.github.patu11.chessservice.friend;

import com.github.patu11.chessservice.user.User;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@Table(name = "friends", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class Friend {
	@javax.persistence.Id
	@GenericGenerator(name = "friendgenerator", strategy = "increment")
	@GeneratedValue(generator = "friendgenerator")
	@Column(name = "friend_id", nullable = false)
	private Long Id;

	@Column
	private boolean status;

	@Column
	private String sender;

	@ManyToOne
	@JoinColumn(name = "user_first")
	private User user1;

	@ManyToOne
	@JoinColumn(name = "user_second")
	private User user2;
}
