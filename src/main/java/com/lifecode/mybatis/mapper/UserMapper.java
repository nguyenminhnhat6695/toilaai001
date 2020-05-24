package com.lifecode.mybatis.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
	
	<T> T getUserById(Map<String,Object> param);

	<T> List<T> selectUsers(Map<String, Object> param);
}
