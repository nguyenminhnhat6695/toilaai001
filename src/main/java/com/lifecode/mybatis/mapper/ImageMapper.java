package com.lifecode.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

	<T> List<T> selectImages(Map<String, Object> param);
}
