package com.lifecode.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lifecode.mybatis.model.CategoryVO;
import com.lifecode.payload.Response;

@RestController
public class HealthCheckController {
	@RequestMapping(value = "/popular-posts", method = RequestMethod.GET)
	public ResponseEntity<Response> getPopularPosts() {

		CategoryVO categoryVO = new CategoryVO();
		
		try {
			return ResponseEntity.ok().body(new Response(HttpStatus.OK,categoryVO));
		} catch (Exception e) {
			System.out.println("test");
		}
		return ResponseEntity.badRequest().body(new Response(HttpStatus.INTERNAL_SERVER_ERROR));
	}
}
