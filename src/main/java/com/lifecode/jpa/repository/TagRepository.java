package com.lifecode.jpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.lifecode.jpa.entity.Tag;;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{

	@Modifying
	@Query("delete from Tag t where t.id in ?1")
	void deleteTagWithIds(List<Long> ids);
	
	boolean existsByTag(String tag);   
}
