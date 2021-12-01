package com.github.patu11.chessservice.profile;

import com.github.patu11.chessservice.comment.Comment;
import com.github.patu11.chessservice.comment.CommentDTO;
import com.github.patu11.chessservice.exceptions.NotFoundException;
import com.github.patu11.chessservice.user.User;
import com.github.patu11.chessservice.user.UserService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class ProfileService {
	private final ProfileRepository profileRepository;
	private final UserService userService;

	public ProfileService(ProfileRepository profileRepository, UserService userService) {
		this.profileRepository = profileRepository;
		this.userService = userService;
	}

	public ProfileDTO getProfile(String username) {
		Profile p = this.profileRepository.findByUserUsername(username).orElseThrow(() -> new NotFoundException("Profile not found"));
		return new ProfileDTO(p);
	}

	public void addComment(CommentDTO comment) {
		Optional<Profile> profile = this.profileRepository.findById(comment.getProfileId());
		Profile p = profile.orElseThrow(() -> new NotFoundException("Profile not found"));
		User u = this.userService.getRawUserByUsername(comment.getAuthor());
		Comment comm = Comment.builder()
				.profile(p)
				.content(comment.getContent())
				.author(u)
				.createdAt(LocalDate.parse(comment.getCreatedAt()))
				.build();

		p.addComment(comm);
		this.profileRepository.save(p);
	}
}
