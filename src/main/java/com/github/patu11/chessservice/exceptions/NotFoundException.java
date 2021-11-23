package com.github.patu11.chessservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
	private final HttpStatus status = HttpStatus.NOT_FOUND;

	public NotFoundException(String message) {
		super(message);
	}
}
