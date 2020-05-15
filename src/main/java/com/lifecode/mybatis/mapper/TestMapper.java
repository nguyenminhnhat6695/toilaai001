package com.lifecode.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TestMapper {

	<T> List<T> selectPosts(Map<String, Object> param);
	
	<T> List<T> selectPostsTest(Map<String, Object> param);
}
