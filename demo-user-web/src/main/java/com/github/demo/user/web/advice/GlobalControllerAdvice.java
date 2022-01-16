package com.github.demo.user.web.advice;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.jtmm.demo.user.bean.Result;
import com.jtmm.demo.user.enums.ResultCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalControllerAdvice {

	@ExceptionHandler({ MethodArgumentNotValidException.class, BindException.class, ConstraintViolationException.class,
			HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class,
			MethodArgumentTypeMismatchException.class, MissingServletRequestParameterException.class })
	public ResponseEntity<Result<?>> badRequestHandler(Exception e) {
		log.warn(e.getMessage());
		if (e instanceof HttpMessageNotReadableException || e instanceof HttpRequestMethodNotSupportedException
				|| e instanceof MethodArgumentTypeMismatchException) {
			return ResponseEntity.badRequest().body(Result.fail(ResultCode.REQUEST_INVALID));
		}

		if (e instanceof MissingServletRequestParameterException) {
			return ResponseEntity.badRequest()
					.body(Result.fail(ResultCode.REQUEST_INVALID.getCode(), "Required parameter '"
							+ ((MissingServletRequestParameterException) e).getParameterName() + "' is not present"));
		}

		if (e instanceof ConstraintViolationException) {
			Set<ConstraintViolation<?>> set = ((ConstraintViolationException) e).getConstraintViolations();
			String message = set.stream().findFirst().map(c -> {
				String[] paths = c.getPropertyPath().toString().split("\\.");
				return paths[paths.length - 1] + " " + c.getMessage();
			}).orElse(ResultCode.REQUEST_INVALID.getMessage());
			return ResponseEntity.badRequest().body(Result.fail(ResultCode.REQUEST_INVALID.getCode(), message));
		}

		BindingResult bindingResult = null;
		if (e instanceof MethodArgumentNotValidException) {
			bindingResult = ((MethodArgumentNotValidException) e).getBindingResult();
		} else if (e instanceof BindException) {
			bindingResult = ((BindException) e).getBindingResult();
		}
		FieldError fieldError = bindingResult.getFieldErrors().get(0);
		return ResponseEntity.badRequest().body(Result.fail(ResultCode.REQUEST_INVALID.getCode(),
				fieldError.getField() + " " + fieldError.getDefaultMessage()));
	}

	@ExceptionHandler({ Throwable.class })
	public ResponseEntity<Result<?>> serverErrorHandler(Throwable e) {
		log.error(e.getMessage(), e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
				.body(Result.fail(ResultCode.INTERNAL_SERVER_ERROR));
	}
}
