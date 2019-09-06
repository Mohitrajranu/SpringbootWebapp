package com.restfulservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulservice.model.Blog;

import java.util.List;

@Repository
public interface BlogRespository extends JpaRepository<Blog, Integer> {

    List<Blog> findByTitleContainingOrContentContaining(String text, String textAgain);

	Blog findById(int blogId);
	void deleteBlogById(int id);
}
