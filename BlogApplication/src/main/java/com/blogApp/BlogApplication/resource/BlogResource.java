package com.blogApp.BlogApplication.resource;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.blogApp.BlogApplication.entity.Blogs;

@Repository
public interface BlogResource extends JpaRepository<Blogs, Long> {
	List<Blogs> findByPublished(boolean published);

}
