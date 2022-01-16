package com.jtmm.demo.user.bean;

import java.io.Serializable;

import com.jtmm.demo.user.enums.ResultCode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel
@Data
@AllArgsConstructor
public class Result<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "0 indicates success, other values indicate failure", example = "0")
	private Integer code;

	@ApiModelProperty(value = "Error message when code is non-zero", example = "OK")
	private String message;
	
	@ApiModelProperty("Business data")
	private T data;
	
	public static <T> Result<T> success() {
		return success(null);
	}
	
	public static <T> Result<T> success(T data) {
		return new Result<>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
	}
	
	public static <T> Result<T> fail(ResultCode resultCode) {
		return fail(resultCode.getCode(), resultCode.getMessage());
	}
	
	public static <T> Result<T> fail(Integer code, String message) {
		return new Result<>(code, message, null);
	}
}
