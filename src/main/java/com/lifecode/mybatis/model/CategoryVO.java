package com.lifecode.mybatis.model;

public class CategoryVO {
	
	private String category_id;
	private String category;
	private String category_img;
	private String number_of_posts;

	public String getCategory_id() {
		return category_id;
	}
	public void setCategory_id(String category_id) {
		this.category_id = category_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCategory_img() {
		return category_img;
	}
	public void setCategory_img(String category_img) {
		this.category_img = category_img;
	}
	public String getNumber_of_posts() {
		return number_of_posts;
	}
	public void setNumber_of_posts(String number_of_posts) {
		this.number_of_posts = number_of_posts;
	}
	
}
