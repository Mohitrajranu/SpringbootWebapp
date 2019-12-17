package com.restfulservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.restfulservice.model.Blog;
import com.restfulservice.repository.BlogRespository;

import java.util.List;
import java.util.Map;

// TODO: Auto-generated Javadoc
/**
 * The Class BlogController.
 * @author Mohit Raj
 */
@RestController
@RequestMapping("/blogmgmt")
public class BlogController {

    /** The blog respository. */
    @Autowired
    BlogRespository blogRespository;

    /**
     * Index.
     *
     * @return the list
     */
    @GetMapping("/blog")
    public List<Blog> index(){
        return blogRespository.findAll();
    }

    /**
     * Show.
     *
     * @param id the id
     * @return the blog
     */
    @GetMapping("/blog/{id}")
    public Blog show(@PathVariable String id){
        int blogId = Integer.parseInt(id);
        return blogRespository.findById(blogId);
    }

    /**
     * Search.
     *
     * @param body the body
     * @return the list
     */
    @PostMapping("/blog/search")
    public List<Blog> search(@RequestBody Map<String, String> body){
        String searchTerm = body.get("text");
        return blogRespository.findByTitleContainingOrContentContaining(searchTerm, searchTerm);
    }

    /**
     * Creates the.
     *
     * @param body the body
     * @return the blog
     */
    @PostMapping("/blog")
    public Blog create(@RequestBody Map<String, String> body){
        String title = body.get("title");
        String content = body.get("content");
        return blogRespository.save(new Blog(title, content));
    }

    /**
     * Update.
     *
     * @param id the id
     * @param body the body
     * @return the blog
     */
    @PutMapping("/blog/{id}")
    public Blog update(@PathVariable String id, @RequestBody Map<String, String> body){
        int blogId = Integer.parseInt(id);
        // getting blog
        Blog blog = blogRespository.findById(blogId);
        blog.setTitle(body.get("title"));
        blog.setContent(body.get("content"));
        return blogRespository.save(blog);
    }

    /**
     * Delete.
     *
     * @param id the id
     * @return true, if successful
     */
    @DeleteMapping("blog/{id}")
    public boolean delete(@PathVariable String id){
        int blogId = Integer.parseInt(id);
        blogRespository.deleteBlogById(blogId);
        return true;
    }


}