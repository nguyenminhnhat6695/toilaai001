package com.lifecode.mybatis.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TagMapper {

	<T> List<T> selectTags(Map<String, Object> param);
}
