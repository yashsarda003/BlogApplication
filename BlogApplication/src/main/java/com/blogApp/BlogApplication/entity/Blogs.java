package com.blogApp.BlogApplication.entity;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "BlogsTable")
public class Blogs {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private long BlogsId;
	private String title;
	private String description;
	private boolean published;

	public Blogs(long blogsId, String title, String description, boolean published) {
		super();
		BlogsId = blogsId;
		this.title = title;
		this.description = description;
		this.published = published;
	}

	public Blogs() {

	}

	public Blogs(String string) {
	}

	public long getBlogsId() {
		return BlogsId;
	}

	public void setBlogsId(long blogsId) {
		BlogsId = blogsId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	@OneToMany(mappedBy = "blogs", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comments> comments = new HashSet<>();

}
