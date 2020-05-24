package com.lifecode.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lifecode.jpa.entity.Category;;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

	boolean existsByCategory(String category);
	
	@Query("select t.category_img from Category t where t.id = :id")
	String findCategoryImgById(Long id);   
}
