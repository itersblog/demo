package com.github.demo.user.service.enums;

public enum Status {
	VALID(0),
	INVALID(-1);
	
	private Integer value;
	
	private Status(Integer value) {
		this.value=value;
	}
	
	public Integer getValue() {
		return value;
	}
}
