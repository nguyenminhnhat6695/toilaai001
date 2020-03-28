package com.lifecode.mybatis.mapper;

import java.util.Map;

import com.lifecode.mybatis.entity.User;

public interface UserMapper {
	
	User getUserById(Map<String,Object> param);
}
