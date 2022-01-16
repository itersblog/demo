package com.github.demo.user.service;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.apache.dubbo.config.annotation.Reference;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.jtmm.demo.user.api.UserService;
import com.jtmm.demo.user.bean.UserDTO;
import com.jtmm.demo.user.enums.ResultCode;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceTest {
	@Reference
	private UserService userService;

	@Test
	void addUser() {
		UserDTO userDTO=new UserDTO();
		userDTO.setEmail("tester@gmail.com");
		userDTO.setName("tester");
		assertEquals(ResultCode.SUCCESS.getCode(), userService.addUser(userDTO).getCode());
	}
	
	@Test
	void editUser() {
		UserDTO userDTO=new UserDTO();
		userDTO.setId(69L);
		userDTO.setEmail("tester@gmail.com");
		userDTO.setName("tester2");
		assertEquals(ResultCode.SUCCESS.getCode(), userService.editUser(userDTO).getCode());
	}
	
	@Test
	void getUser() {
		assertEquals(ResultCode.SUCCESS.getCode(), userService.getUser(69L).getCode());
	}
	
	@Test
	void deleteUser() {
		assertEquals(ResultCode.SUCCESS.getCode(), userService.deleteUser(Arrays.asList(1L, 2L)).getCode());
	}

}
