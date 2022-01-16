package com.github.demo.user.service.mapper;

import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.github.demo.user.service.entity.User;

public interface UserMapper extends BaseMapper<User> {
	int updateStatus(List<Long> idList, Integer status);
}
