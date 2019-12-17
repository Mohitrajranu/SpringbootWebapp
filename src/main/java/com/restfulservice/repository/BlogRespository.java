package com.restfulservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.restfulservice.model.Blog;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Interface BlogRespository.
 *  @author Mohit Raj
 */
@Repository
public interface BlogRespository extends JpaRepository<Blog, Integer> {

    /**
     * Find by title containing or content containing.
     *
     * @param text the text
     * @param textAgain the text again
     * @return the list
     */
    List<Blog> findByTitleContainingOrContentContaining(String text, String textAgain);

	/**
	 * Find by id.
	 *
	 * @param blogId the blog id
	 * @return the blog
	 */
	Blog findById(int blogId);
	
	/**
	 * Delete blog by id.
	 *
	 * @param id the id
	 */
	void deleteBlogById(int id);
}
