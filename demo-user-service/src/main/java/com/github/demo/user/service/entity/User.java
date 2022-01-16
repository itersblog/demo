package com.github.demo.user.service.entity;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@TableId(type = IdType.AUTO)
	private Long id;
	
	private String email;
	
	private String name;
	
	private Integer status;
	
	private Date createTime;

	private Date modifyTime;
}
