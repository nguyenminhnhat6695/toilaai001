package com.lifecode.payload;

import org.springframework.http.HttpStatus;

public class Response {

	public int status;
	public Object data;
	public String message;
	public String accessToken;
	
	public Response(HttpStatus status) {
		this.status = status.value();
		this.data = null;
		this.message = status==HttpStatus.OK?"Sucessfully":"An error occurred !";
		this.accessToken = null;
	}
	
	public Response(String message) {
		this.message = message;
	}

	public Response(HttpStatus status, Object data) {
		this.status = status.value();
		this.data = data;
		this.message = status==HttpStatus.OK?"Sucessfully":"An error occurred !";
	}
	
	public Response(HttpStatus status, Object data, String message) {
		this.status = status.value();
		this.data = data;
		this.message = message;
	}
	
	public Response(HttpStatus status, Object data, String message, String accessToken) {
		this.status = status.value();
		this.data = data;
		this.message = message;
		this.accessToken = accessToken;
	}
}