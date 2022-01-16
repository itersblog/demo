package com.github.demo.user.web.controller;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jtmm.demo.user.bean.UserDTO;
import com.jtmm.demo.user.enums.ResultCode;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserControllerTest {
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private WebApplicationContext webApplicationContext;

	private MockMvc mockMvc;

	@BeforeEach
	public void setUp() throws Exception {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	void addUser() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setEmail("tester@gmail.com");
		userDTO.setName("tester");

		mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDTO))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is(ResultCode.SUCCESS.getCode())));
	}

	@Test
	void editUser() throws Exception {
		UserDTO userDTO = new UserDTO();
		userDTO.setId(68L);
		userDTO.setEmail("tester@gmail.com");
		userDTO.setName("tester2");

		mockMvc.perform(MockMvcRequestBuilders.put("/user").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDTO))).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is(ResultCode.SUCCESS.getCode())));
	}

	@Test
	void getUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.get("/user/{1}", 67L)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is(ResultCode.SUCCESS.getCode())));
	}

	@Test
	void deleteUser() throws Exception {
		mockMvc.perform(MockMvcRequestBuilders.delete("/user").param("idList", "1,2"))
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.code", Matchers.is(ResultCode.SUCCESS.getCode())));
	}

}
