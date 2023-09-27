package com.blogApp.BlogApplication.service;

import com.blogApp.BlogApplication.entity.Blogs;
import com.blogApp.BlogApplication.entity.Comments;
import com.blogApp.BlogApplication.exception.CustomException;
import com.blogApp.BlogApplication.resource.BlogResource;
import com.blogApp.BlogApplication.resource.CommentResource;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentResource commentResource;

    @Autowired
    private BlogResource blogResource;

    // // Retrieving All comments of a blog
    public List<Comments> getAllComments(long blogId) {
        Boolean exists = blogResource.existsById(blogId);
        if (exists) {
            Blogs blog = blogResource.findById(blogId).orElse(null);
            if (blog != null) {
                List<Comments> checkComment = commentResource.findByBlogs(blog);
                if (checkComment.isEmpty()) {
                    throw new CustomException("703", "There are No Comments on this Blog !");
                } else {
                    return commentResource.findByBlogs(blog);
                }
            } else {
                throw new CustomException("703",
                        "NullPointerException : The Blog is Returning The Null Value, maybe due to absence of blogs or the given ID Doesn't have any blog associated with it....");
            }
        } else {
            throw new CustomException("703", "The Given Blog Id Doesn't Exists in Database");
        }
    }

    // // Post a comment on a particular blog
    public Comments postComment(String content, long blogId) {
        if (content == null || content.trim().isEmpty()) {
            throw new CustomException("707", "Please Enter some content for the comment");
        }
        Boolean exists = blogResource.existsById(blogId);
        if (exists) {

            Blogs blog = blogResource.findById(blogId).orElse(null);
            if (blog != null) {
                try {
                    Comments comment = new Comments();
                    comment.setContent(content);
                    comment.setBlogs(blog);
                    return commentResource.save(comment);
                } catch (IllegalArgumentException e) {
                    throw new CustomException("707", "Please Check your given ID : " + e.getMessage());
                }
            } else {
                throw new CustomException("707", "There dosen't exists any Blog with ID:" + blogId);
            }
        } else {
            throw new CustomException("707", "The Given Blog Id does not exists in the Database !");
        }
    }

    // To fetch comment by ID
    public String fetchByComments(Long id) {
        boolean check = commentResource.existsById(id);
        if (check) {
            try {
                Comments co = commentResource.findById(id).orElse(null);
                if (co == null) {
                    throw new CustomException("707",
                            "NullPointerException : The given comment is returning null value");
                } else {
                    return co.getContent();
                }
            } catch (IllegalArgumentException e) {
                throw new CustomException("707", "Please Check your given ID : " + e.getMessage());
            }
        } else {
            throw new CustomException("707", "There dosen't exists any comment with ID:" + id);
        }
    }

    // Update a Comment
    public Comments changeComment(long id, Comments updatedComment) {
        // Load the existing comment

        Boolean commentIdExists = commentResource.existsById(id);
        if (commentIdExists) {
            Comments existingComment = commentResource.findById(id)
                    .orElseThrow(() -> new RuntimeException("Comment ID Does Not Exist"));
            // Get the associated blog from the existing comment
            Blogs blog = existingComment.getBlogs();
            if (blog == null) {
                throw new RuntimeException("Comment does not have an associated blog");
            }
            // Make changes to the existing comment
            existingComment.setContent(updatedComment.getContent());
            // Save the updated comment
            Comments savedComment = commentResource.save(existingComment);
            return savedComment;
        } else {
            throw new CustomException("708",
                    "The Given Comment ID Doesn't Exists Please Check the comment with ID : " + id);
        }

    }

    // // Delete a Comment by Comment ID
    public String DeleteComment(long id) {
        boolean checkCommentId = commentResource.existsById(id);
        if (checkCommentId) {
            try {
                commentResource.deleteById(id);
                return "Comment Deleted Successfully";

            } catch (IllegalArgumentException e) {
                throw new CustomException("709", "Please Check your given ID : " + e.getMessage());
            }
        } else {
            throw new CustomException("709", "There dosen't exists any comment with ID:" + id);
        }
    }

    // Delete All Comments of a Particular Blog
    public String DeleteAllCommentsByBlogId(long blogId) {
        boolean exists = blogResource.existsById(blogId);
        if (exists) {
            Blogs blog = blogResource.findById(blogId).orElse(null);
            if (blog != null) {

                List<Comments> commentsList = commentResource.findByBlogs(blog);
                if (commentsList.isEmpty()) {
                    throw new CustomException("703", "There are No Comments on this Blog with given ID : " + blogId);
                } else {
                    for (int i = 0; i < commentsList.size(); i++) {
                        commentResource.deleteById(commentsList.get(i).getId());
                    }
                    return "All Comments Deleted Successfully";
                }
            } else {
                throw new CustomException("710", "NullPointerException The blog is returning the null value");
            }
        } else {
            throw new CustomException("710", "The Given Blog ID Doesn't Exists in the Database");
        }
    }
}
