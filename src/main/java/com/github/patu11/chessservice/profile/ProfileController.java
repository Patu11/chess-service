package com.github.patu11.chessservice.profile;

import com.github.patu11.chessservice.comment.Comment;
import com.github.patu11.chessservice.comment.CommentDTO;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Log4j
@RestController
@RequestMapping("/profiles")
public class ProfileController {
	private final ProfileService profileService;

	@Autowired
	public ProfileController(ProfileService profileService) {
		this.profileService = profileService;
	}

	@GetMapping
	public ProfileDTO getProfile(@RequestHeader("username") String username) {
		return this.profileService.getProfile(username);
	}

	@PostMapping("/addcomment")
	public void addComment(@RequestBody CommentDTO comment) {
		log.info(comment);
		this.profileService.addComment(comment);
	}
}