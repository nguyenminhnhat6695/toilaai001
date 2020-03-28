package com.lifecode.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.lifecode.mybatis.entity.Category;
import com.lifecode.mybatis.entity.Image;
import com.lifecode.mybatis.entity.Post;
import com.lifecode.mybatis.entity.Tag;
import com.lifecode.mybatis.entity.User;

@Mapper
public interface HomeMapper {
	
	int selectPostsTotCnt(Map<String, Object> param);
	
	List<Post> selectPosts(Map<String, Object> param);
	
	List<Post> selectHotPosts();

	List<Post> selectPopularPosts();
	
	List<Post> selectRecentPosts();
	
	List<Category> selectCategories(Map<String, Object> param);

	List<User> selectUsers(Map<String, Object> param);

	List<Image> selectImages(Map<String, Object> param);

	List<Post> selectOldPosts(Map<String, Object> param);

	List<Tag> selectTags(Map<String, Object> param);

	Post getPostById(String postId);
}
