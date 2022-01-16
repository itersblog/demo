package com.github.demo.mail.service;

import static org.junit.Assert.assertTrue;

import org.apache.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jtmm.demo.mail.api.MailService;
import com.jtmm.demo.mail.bean.MailDTO;

@RunWith(SpringRunner.class)
@SpringBootTest
class MailServiceTest {
	@Reference
	private MailService mailService;

	@Test
	void sendMail() {
		assertTrue(mailService
				.sendMail(MailDTO.builder().email("tester@gmail.com").subject("Test Subject").body("Test Body").build()));
	}

}
