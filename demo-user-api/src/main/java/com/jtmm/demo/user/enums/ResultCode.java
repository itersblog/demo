package com.jtmm.demo.user.enums;

public enum ResultCode {
	SUCCESS(0,"OK"),
	EMAIL_EXISTS(20001,"The email address already exists"),
	USER_NOT_EXISTS(20002,"The user does not exist"),
	REQUEST_INVALID(40001,"The request is invalid"),
	INTERNAL_SERVER_ERROR(50001,"Internal server error, please try again later");
	
	private Integer code;
	private String message;
	
	private ResultCode(Integer code,String message) {
		this.code=code;
		this.message=message;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
