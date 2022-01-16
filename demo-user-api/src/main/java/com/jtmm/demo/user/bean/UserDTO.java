package com.jtmm.demo.user.bean;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.Length;

import com.jtmm.demo.user.validation.ValidGroup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class UserDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "User id", example = "1", required = true)
	@Null(groups = {ValidGroup.Insert.class})
	@NotNull(groups = {ValidGroup.Update.class})
	private Long id;
	
	@ApiModelProperty(value = "Email address", example = "xxx@gmail.com", required = true)
	@NotBlank
	@Email
	@Length(min = 5, max = 80)
	private String email;
	
	@ApiModelProperty(value = "User name", example = "Jackson", required = true)
	@NotBlank
	@Length(min = 2, max = 50)
	private String name;
	
	@ApiModelProperty(value = "User status, 0 is valid, -1 is invalid(deleted)", example = "0")
	@Null
	private Integer status;
	
	@ApiModelProperty(value = "Create time", example = "2022-01-15 10:36:52")
	@Null
	private Date createTime;

	@ApiModelProperty(value = "Modify time", example = "2022-01-16 08:26:11")
	@Null
	private Date modifyTime;
}
