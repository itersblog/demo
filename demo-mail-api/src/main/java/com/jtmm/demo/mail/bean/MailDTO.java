package com.jtmm.demo.mail.bean;

import java.io.Serializable;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MailDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String subject;
	
	private String body;
	
	private String email;
	
}
