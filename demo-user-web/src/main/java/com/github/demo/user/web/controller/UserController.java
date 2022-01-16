package com.github.demo.user.web.controller;

import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.apache.dubbo.config.annotation.Reference;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.jtmm.demo.user.api.UserService;
import com.jtmm.demo.user.bean.Result;
import com.jtmm.demo.user.bean.UserDTO;
import com.jtmm.demo.user.validation.ValidGroup;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "User API")
@Validated
@RestController
@RequestMapping("user")
public class UserController {
	@Reference
	private UserService userService;

	/**
	 * Register a new user, if the registration is successful, a registration
	 * success email will be sent
	 * @param userDTO
	 * @return
	 */
	@ApiOperation("Add User")
	@ApiOperationSupport(includeParameters = {"userDTO.email", "userDTO.name"})
	@PostMapping
	public Result<Object> addUser(@RequestBody @Validated({ ValidGroup.Insert.class }) UserDTO userDTO) {
		return userService.addUser(userDTO);
	}

	/**
	 * Edit user information
	 * @param userDTO
	 * @return
	 */
	@ApiOperation("Edit User")
	@ApiOperationSupport(includeParameters = {"userDTO.id", "userDTO.email", "userDTO.name"})
	@PutMapping
	public Result<Object> editUser(@RequestBody @Validated({ ValidGroup.Update.class }) UserDTO userDTO) {
		return userService.editUser(userDTO);
	}

	/**
	 * Get user information by id
	 * @param id
	 * @return
	 */
	@ApiOperation("Get User")
	@GetMapping("{id}")
	public Result<UserDTO> getUser(@PathVariable Long id) {
		return userService.getUser(id);
	}

	/**
	 * Logically delete users, support batch deletion
	 * @param idList
	 * @return
	 */
	@ApiOperation("Delete User")
	@DeleteMapping
	public Result<Object> deleteUser(@RequestParam @NotEmpty List<Long> idList) {
		return userService.deleteUser(idList);
	}
}
