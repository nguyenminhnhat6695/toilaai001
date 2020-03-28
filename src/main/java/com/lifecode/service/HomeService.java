package com.lifecode.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lifecode.mybatis.entity.Category;
import com.lifecode.mybatis.entity.Image;
import com.lifecode.mybatis.entity.Post;
import com.lifecode.mybatis.entity.Tag;
import com.lifecode.mybatis.entity.User;
import com.lifecode.mybatis.mapper.HomeMapper;
import com.lifecode.utils.Utils;

@Service
public class HomeService {
	
	private List<Post> list;
	
	private List<Tag> tags;
	
	private List<User> users;
	
	private List<Image> images;

	@Resource
	private HomeMapper homeMapper;
	
	public List<Category> getCategories(Map<String, Object> param) {
		return homeMapper.selectCategories(param);
	}

	public List<Tag> getTags(Map<String, Object> param) {
		return homeMapper.selectTags(param);
	}

	public List<Post> getPopularPosts() {
		list = homeMapper.selectPopularPosts();
		return getDetailPosts(list);
	}

	public List<Post> getHotPosts() {
		list = homeMapper.selectHotPosts();
		return getDetailPosts(list);
	}

	public List<Post> getRecentPosts() {
		list = homeMapper.selectRecentPosts();
		return getDetailPosts(list);
	}
	
	public Map<String,Object> getPosts(Map<String, Object> param) {
		
		Map<String,Object> result = new HashMap<String,Object>();
		
		String page = (String) param.get("page");
		String recordsNo = (String) param.get("records_no");
		int pageInt = 0;
		int lastPage = 0;

		// get list of page
		if(Utils.isInteger(page) && Utils.isInteger(recordsNo)) {
			
			int recordsNoInt = Integer.parseInt(recordsNo);
			if(recordsNoInt == 0) {
				return result;
			}
			
			int totalPosts = homeMapper.selectPostsTotCnt(param);
			if(totalPosts < recordsNoInt) {
				list = homeMapper.selectPosts(param);
			} else {
				lastPage = totalPosts/recordsNoInt + ((totalPosts%recordsNoInt)>0?1:0);
				pageInt = Integer.parseInt(page);
				pageInt = pageInt<=0?lastPage:pageInt>lastPage?1:pageInt;
				
				int startPost = (pageInt-1)*recordsNoInt;
				param.put("start_post", startPost);
				list = homeMapper.selectPosts(param);
				
				result.put("page_of_post", pageInt);
				result.put("last_page", lastPage);
			}
		} else {
			list =  homeMapper.selectPosts(param);
			result.put("page", 1);
			result.put("last_page", 1);
		}
		
		result.put("list", getDetailPosts(list));
		return result;
	}
	
	public List<Post> getOldPosts(Map<String, Object> param) {
		param.put("start_post", 1);
		list = homeMapper.selectOldPosts(param);
		return getDetailPosts(list);
	}

	private List<Post> getDetailPosts(List<Post> posts) {
		if(posts.isEmpty()) {
			return new ArrayList<Post>();
		}
		for(Post e:posts) {
			getDetailPost(e);
		}
		return posts;
	}
	
	private Post getDetailPost(Post post) {
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("post_id",post.getPost_id());
		
		tags = homeMapper.selectTags(param);
		post.setTags(tags);
		
		users = homeMapper.selectUsers(param);
		post.setUsers(users);
		
		images = homeMapper.selectImages(param);
		post.setImages(images);
		
		return post;
	}

	public Post getPostById(String postId) {
		Post post = homeMapper.getPostById(postId);
		return getDetailPost(post);
	}
}
