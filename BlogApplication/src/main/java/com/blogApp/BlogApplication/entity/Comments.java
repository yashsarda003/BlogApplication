package com.blogApp.BlogApplication.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "comments table")

public class Comments {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String content;

	// @ManyToOne(fetch = FetchType.LAZY)
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "blogs_id", nullable = false)
	private Blogs blogs;

	public Comments(long id, String content, Blogs blogs) {
		this.id = id;
		this.content = content;
		this.blogs = blogs;
	}

	public Comments() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Blogs getBlogs() {
		return blogs;
	}

	public void setBlogs(Blogs blogs) {
		this.blogs = blogs;
	}
}
