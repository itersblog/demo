package com.github.demo.user.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.BeanUtils;
import org.springframework.dao.DuplicateKeyException;

import com.github.demo.user.service.entity.User;
import com.github.demo.user.service.enums.Status;
import com.github.demo.user.service.mapper.UserMapper;
import com.github.demo.user.service.utils.Constants;
import com.jtmm.demo.mail.api.MailService;
import com.jtmm.demo.mail.bean.MailDTO;
import com.jtmm.demo.user.api.UserService;
import com.jtmm.demo.user.bean.Result;
import com.jtmm.demo.user.bean.UserDTO;
import com.jtmm.demo.user.enums.ResultCode;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

	@Reference
	private MailService mailService;
	
	@Resource
	private UserMapper userMapper;

	/**
	 * Register a new user, if the registration is successful, a registration success email will be sent
	 * @param userDTO
	 * @return
	 */
	@Override
	public Result<Object> addUser(UserDTO userDTO) {
		try {
			log.info("userDTO={}", userDTO);
			User user = new User();
			BeanUtils.copyProperties(userDTO, user);
			user.setStatus(Status.VALID.getValue());
			userMapper.insert(user);
			
			// Asynchronously send emails to the user's registered email address
			mailService.asyncSendMail(MailDTO.builder().email(user.getEmail())
					.subject(Constants.REGISTRATION_SUCCESS_SUBJECT).body(Constants.REGISTRATION_SUCCESS_BODY).build());
		} catch (DuplicateKeyException e) {
			return Result.fail(ResultCode.EMAIL_EXISTS);
		}
		return Result.success();
	}

	/**
	 * Edit user information
	 * @param userDTO
	 * @return
	 */
	@Override
	public Result<Object> editUser(UserDTO userDTO) {
		try {
			log.info("userDTO={}", userDTO);
			User user = new User();
			BeanUtils.copyProperties(userDTO, user);
			if (userMapper.updateById(user) < 1) {
				return Result.fail(ResultCode.USER_NOT_EXISTS);
			}
		} catch (DuplicateKeyException e) {
			return Result.fail(ResultCode.EMAIL_EXISTS);
		}
		return Result.success();
	}

	/**
	 * Get user information by id
	 * @param id
	 * @return
	 */
	@Override
	public Result<UserDTO> getUser(Long id) {
		log.info("id={}", id);
		User user = userMapper.selectById(id);
		if (user == null) {
			return Result.fail(ResultCode.USER_NOT_EXISTS);
		}
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(user, userDTO);
		return Result.success(userDTO);
	}

	/**
	 * Logically delete users, support batch deletion
	 * @param idList
	 * @return
	 */
	@Override
	public Result<Object> deleteUser(List<Long> idList) {
		log.info("idList={}", idList);
		userMapper.updateStatus(idList, Status.INVALID.getValue());
		return Result.success();
	}

}
