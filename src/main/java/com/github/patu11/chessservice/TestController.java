package com.github.patu11.chessservice;

import com.github.patu11.chessservice.user.User;
import lombok.extern.log4j.Log4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Log4j
public class TestController {

	@GetMapping("/test")
	public String test() {
		log.trace("Trace");
		log.debug("Debug");
		log.error("Error");
		log.fatal("Fatal");
		log.info("Info " + new User("asd@gmail.com", "test_username", "testpassword"));
		return "Test output";
	}
}
