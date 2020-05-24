package com.lifecode.service;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lifecode.common.Const;
import com.lifecode.exception.BusinessException;
import com.lifecode.jpa.entity.Category;
import com.lifecode.jpa.repository.CategoryRepository;
import com.lifecode.mybatis.mapper.CategoryMapper;
import com.lifecode.mybatis.model.CategoryVO;
import com.lifecode.utils.FileUtil;

@Service
public class CategoryService {

	@Resource
	private CategoryMapper categoryMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	public List<CategoryVO> getCategories(Map<String, Object> param) {
		return categoryMapper.selectCategories(param);
	}
	
	public Boolean isExistsCategory(String category) {
		if(StringUtils.isEmpty(category)) 
			return false;
		return categoryRepository.existsByCategory(category);
	}
	
	public Category saveCategory(String categoryName, String categoryImage) throws BusinessException {
		
		if(StringUtils.isEmpty(categoryName) || categoryRepository.existsByCategory(categoryName)) {
			throw new BusinessException("Category name \""+categoryName+"\" already exists !");
		}
		
		String categoryImg = FileUtil.saveBase64Image(categoryImage, Const.IMG_CATEGORY_PATH,categoryName);
		Category category = new Category(categoryName, categoryImg);
		
		return categoryRepository.save(category);
	}

	public void removeCategory(Long categoryId) {
		String categoryImg = categoryRepository.findCategoryImgById(categoryId);
		FileUtil.deleteImage(Const.IMG_CATEGORY_PATH,categoryImg);
		categoryRepository.deleteById(categoryId);
	}
}
