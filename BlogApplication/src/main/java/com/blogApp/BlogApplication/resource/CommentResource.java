package com.blogApp.BlogApplication.resource;

import com.blogApp.BlogApplication.entity.Blogs;
import com.blogApp.BlogApplication.entity.Comments;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentResource extends JpaRepository<Comments, Long> {
    List<Comments> findByBlogs(Blogs blogs);
}
