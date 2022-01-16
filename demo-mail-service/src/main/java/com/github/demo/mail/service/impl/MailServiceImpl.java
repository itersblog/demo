package com.github.demo.mail.service.impl;


import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.jtmm.demo.mail.api.MailService;
import com.jtmm.demo.mail.bean.MailDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MailServiceImpl implements MailService {
	@Value("${spring.mail.username}")
	private String username;
	
	@Value("${spring.mail.fromName}")
	private String fromName;
	
	@Value("${spring.mail.retries}")
	private Integer retries;
	
	@Resource
	private JavaMailSender javaMailSender;
	
	@Resource
	private ThreadPoolTaskExecutor executor;

	/**
	 * Send emails synchronously
	 * @param mailDTO
	 * @return
	 */
	@Override
	public boolean sendMail(MailDTO mailDTO) {
		try {
			MimeMessage mimeMessage = javaMailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
			helper.setText(mailDTO.getBody(), true);
			helper.setTo(mailDTO.getEmail());
			helper.setSubject(mailDTO.getSubject());
			helper.setFrom(fromName+"<"+username+">");
			javaMailSender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return false;
	}

	/**
	 * Send mail asynchronously, if sending fails, it will retry 5 times
	 * @param mailDTO
	 */
	public void asyncSendMail(MailDTO mailDTO) {
		executor.execute(() -> {
			try {
				int count=0;
				while(true){
					boolean success=sendMail(mailDTO);
					if(success || ++count==retries)
						break;
					Thread.sleep(1000);
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		});
	}
	
}
