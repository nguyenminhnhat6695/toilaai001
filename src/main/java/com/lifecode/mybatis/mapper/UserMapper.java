package com.lifecode.mybatis.mapper;

import java.util.Map;

public interface UserMapper {
	
	<T> T getUserById(Map<String,Object> param);
}
