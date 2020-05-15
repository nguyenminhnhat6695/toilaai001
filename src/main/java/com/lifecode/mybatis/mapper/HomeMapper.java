package com.lifecode.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface HomeMapper {
	
	int selectPostsTotCnt(Map<String, Object> param);
	
	<T> List<T> selectPosts(Map<String, Object> param);
	
	<T> List<T> selectHotPosts();

	<T> List<T> selectPopularPosts();
	
	<T> List<T> selectRecentPosts();

	<T> List<T> selectCategories(Map<String, Object> param);

	<T> List<T> selectUsers(Map<String, Object> param);

	<T> List<T> selectImages(Map<String, Object> param);

	<T> List<T> selectOldPosts(Map<String, Object> param);

	<T> List<T> selectTags(Map<String, Object> param);

	<T> T getPostById(String postId);
}
