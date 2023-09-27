package com.blogApp.BlogApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.blogApp.BlogApplication.entity.Comments;
import com.blogApp.BlogApplication.exception.ControllerException;
import com.blogApp.BlogApplication.exception.CustomException;
import com.blogApp.BlogApplication.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	@Autowired
	public CommentService commentService;

	@GetMapping("/blogs/:{id}/comments")
	public ResponseEntity<?> getBlogComments(@PathVariable("id") long id) {
		try {
			List<Comments> CommentsList = commentService.getAllComments(id);
			return ResponseEntity.status(HttpStatus.OK).body(CommentsList);
		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("711", "Something went wrong in the controller class ");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// Post a comment on particular blog
	@PostMapping("/blogs/:{id}/comments")
	public ResponseEntity<?> getBlogComments(@PathVariable("id") long id, @RequestBody Comments c) {
		try {
			Comments comment = commentService.postComment(c.getContent(), id);
			return ResponseEntity.status(HttpStatus.OK).body(comment);

		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("711", "Something went wrong in the controller class ");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// Get a comment by Comment Id
	@GetMapping("/comments/:{id}")
	public ResponseEntity<?> getCommentById(@PathVariable Long id) {
		try {
			String comment = commentService.fetchByComments(id);
			return ResponseEntity.status(HttpStatus.OK).body(comment);
		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("711", "Something went wrong in the controller class ");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// Update a comment
	@PutMapping("/comments/:{id}")
	public ResponseEntity<?> updateComment(@PathVariable(value = "id") long id, @RequestBody Comments comments) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(commentService.changeComment(id, comments));
		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("711", "Something went wrong in the controller class ");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// Delete a comment by ID
	@DeleteMapping("/comments/:{id}")
	public ResponseEntity<?> DeleteCommentById(@PathVariable(value = "id") long id) {
		try {
			String delComment = commentService.DeleteComment(id);
			return ResponseEntity.status(HttpStatus.OK).body(delComment);

		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("711", "Something went wrong in the controller class ");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// Delete all comments of a Particular Blog
	@DeleteMapping("/blogs/:{blogsId}/comments")
	public ResponseEntity<?> DeleteAllCommentBlog(@PathVariable(value = "blogsId") long blogsId) {
		try {
			String DelAllComment = commentService.DeleteAllCommentsByBlogId(blogsId);
			return ResponseEntity.status(HttpStatus.CREATED).body(DelAllComment);
		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("711", "Something went wrong in the controller class ");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}
}
