package com.github.patu11.chessservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.CONFLICT)
public class UserAlreadyExistException extends RuntimeException {
	private final HttpStatus status = HttpStatus.CONFLICT;

	public UserAlreadyExistException(String message) {
		super(message);
	}
}
