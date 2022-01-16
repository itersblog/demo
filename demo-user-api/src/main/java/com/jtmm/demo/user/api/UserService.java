package com.jtmm.demo.user.api;

import java.util.List;

import com.jtmm.demo.user.bean.Result;
import com.jtmm.demo.user.bean.UserDTO;

public interface UserService {
	/**
	 * Register a new user, if the registration is successful, a registration success email will be sent
	 * @param userDTO
	 * @return
	 */
	Result<Object> addUser(UserDTO userDTO);
	
	/**
	 * Edit user information
	 * @param userDTO
	 * @return
	 */
	Result<Object> editUser(UserDTO userDTO);
	
	/**
	 * Get user information by id
	 * @param id
	 * @return
	 */
	Result<UserDTO> getUser(Long id);
	
	/**
	 * Logically delete users, support batch deletion
	 * @param idList
	 * @return
	 */
	Result<Object> deleteUser(List<Long> idList);
}
