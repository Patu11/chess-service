package com.github.patu11.chessservice.friend;

import com.github.patu11.chessservice.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
	Optional<Friend> findFriendByUser1AndUser2(User user1, User user2);
}