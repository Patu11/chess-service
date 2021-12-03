package com.github.patu11.chessservice.friend;

import com.github.patu11.chessservice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
	Optional<Friend> findFriendByUser1AndUser2(User user1, User user2);

	Boolean existsByUser1AndUser2(User user1, User user2);

	List<Friend> findAllByUser1(User user1);

	List<Friend> findAllByUser2(User user2);
}
