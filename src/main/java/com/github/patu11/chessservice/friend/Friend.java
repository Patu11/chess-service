package com.github.patu11.chessservice.friend;

import com.github.patu11.chessservice.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Getter
@Setter
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

	@ManyToOne
	@JoinColumn(name = "user_first")
	private User user1;

	@ManyToOne
	@JoinColumn(name = "user_second")
	private User user2;
}
