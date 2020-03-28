package com.lifecode.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lifecode.mybatis.entity.Category;
import com.lifecode.mybatis.entity.Post;
import com.lifecode.mybatis.entity.Tag;
import com.lifecode.service.HomeService;
import com.lifecode.utils.Utils;

@RestController
@RequestMapping(value = "api/home")
@CrossOrigin(origins = {"http://localhost:3000","http://localhost:3001"})
public class HomeController {
	
	protected Logger logger = LoggerFactory.getLogger(HomeController.class);

	@Autowired
	private HomeService homeService;

	@RequestMapping(value = "/categories", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String,Object>> getCategories(@RequestParam Map<String,Object> param) {

		try {
			List<Category> result = homeService.getCategories(param);
			return ResponseEntity.ok().body(Utils.responseOK(result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.badRequest().body(Utils.responseERROR());
	}
	
	@RequestMapping(value  = "/tags", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String,Object>> getTagsByPostId(@RequestParam Map<String,Object> param) {

		try {
			List<Tag> result = homeService.getTags(param);
			return ResponseEntity.ok().body(Utils.responseOK(result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.badRequest().body(Utils.responseERROR());
	}
	@RequestMapping(value = "/hot-posts", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String,Object>> getHotPosts() {

		try {
			List<Post> result = homeService.getHotPosts();
			return ResponseEntity.ok().body(Utils.responseOK(result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.badRequest().body(Utils.responseERROR());
	}
	
	@RequestMapping(value = "/recent-posts", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String,Object>> getRecentPosts() {

		try {
			List<Post> result = homeService.getRecentPosts();
			return ResponseEntity.ok().body(Utils.responseOK(result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.badRequest().body(Utils.responseERROR());
	}
	
	@RequestMapping(value = "/popular-posts", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String,Object>> getPopularPosts() {

		try {
			List<Post> result = homeService.getPopularPosts();
			return ResponseEntity.ok().body(Utils.responseOK(result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.badRequest().body(Utils.responseERROR());
	}

	@RequestMapping(value = "/old-posts", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String,Object>> getOldPosts(@RequestParam Map<String,Object> param) {

		try {
			List<Post> result = homeService.getOldPosts(param);
			return ResponseEntity.ok().body(Utils.responseOK(result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.badRequest().body(Utils.responseERROR());
	}
	
	@RequestMapping(value = "/posts", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String,Object>> getPosts(@RequestParam Map<String,Object> param) {

		try {
			Map<String,Object> result = homeService.getPosts(param);
			return ResponseEntity.ok().body(Utils.responseOK(result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.badRequest().body(Utils.responseERROR());
	}

	@RequestMapping(value = "/post/{post_id}", method = RequestMethod.GET, produces = {
			MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Map<String,Object>> getPostById(@PathVariable(value = "post_id") String postId) {

		try {
			Post result = homeService.getPostById(postId);
			return ResponseEntity.ok().body(Utils.responseOK(result));
		} catch (Exception e) {
			logger.error("Excecption : {}", ExceptionUtils.getStackTrace(e));
		}
		return ResponseEntity.badRequest().body(Utils.responseERROR());
	}
}
