package com.github.patu11.chessservice.config;

import com.github.patu11.chessservice.exceptions.BadRequestDataException;
import com.github.patu11.chessservice.exceptions.ErrorBody;
import com.github.patu11.chessservice.exceptions.UserAlreadyExistException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = UserAlreadyExistException.class)
	protected ResponseEntity<?> handleUserAlreadyExistException(UserAlreadyExistException exception, WebRequest request) {
		ErrorBody errorBody = new ErrorBody(exception.getStatus().value(), exception.getMessage());
		return handleExceptionInternal(exception, errorBody, new HttpHeaders(), exception.getStatus(), request);
	}

	@ExceptionHandler(value = BadRequestDataException.class)
	protected ResponseEntity<?> handleBadRequestDataException(BadRequestDataException exception, WebRequest request) {
		ErrorBody errorBody = new ErrorBody(exception.getStatus().value(), exception.getMessage());
		return handleExceptionInternal(exception, errorBody, new HttpHeaders(), exception.getStatus(), request);
	}
}
