package com.lifecode.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.lifecode.mybatis.mapper.TestMapper;
import com.lifecode.mybatis.model.PostVO;

@Service
public class TestService {

	@Resource
	private TestMapper testMapper;

	public List<PostVO> getPosts(Map<String, Object> param) {
		List<Map<String,Object>> test = testMapper.selectPostsTest(param);
			
		return testMapper.selectPosts(param);
	}
	
//	public static void main(String[] args) {
//		FieldMap<PostEnum> t = new FieldMap<PostEnum>();
//		t.get(key)
//	}
}
