package com.jtmm.demo.mail.api;

import com.jtmm.demo.mail.bean.MailDTO;

public interface MailService {
	
	/**
	 * Send emails synchronously
	 * @param mailDTO
	 * @return
	 */
	boolean sendMail(MailDTO mailDTO);
	
	/**
	 * Send mail asynchronously, if sending fails, it will retry 3 times
	 * @param mailDTO
	 */
	void asyncSendMail(MailDTO mailDTO);
	
}
