package com.restfulservice.model;

import javax.persistence.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Blog.
 * @author Mohit Raj
 */
@Entity
public class Blog {
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    /** The title. */
    private String title;
    
    /** The content. */
    private String content;

    /**
     * Instantiates a new blog.
     */
    public Blog() {  }

    /**
     * Instantiates a new blog.
     *
     * @param title the title
     * @param content the content
     */
    public Blog(String title, String content) {
        this.setTitle(title);
        this.setContent(content);
    }

    /**
     * Instantiates a new blog.
     *
     * @param id the id
     * @param title the title
     * @param content the content
     */
    public Blog(int id, String title, String content) {
        this.setId(id);
        this.setTitle(title);
        this.setContent(content);
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets the title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the title.
     *
     * @param title the new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Sets the content.
     *
     * @param content the new content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
