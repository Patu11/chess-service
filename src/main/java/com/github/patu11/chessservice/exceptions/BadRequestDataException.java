package com.github.patu11.chessservice.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.NO_CONTENT)
public class BadRequestDataException extends RuntimeException {
	private final HttpStatus status = HttpStatus.NO_CONTENT;

	public BadRequestDataException(String message) {
		super(message);
	}
}
