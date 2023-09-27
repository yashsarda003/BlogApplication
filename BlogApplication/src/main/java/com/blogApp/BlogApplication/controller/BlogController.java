package com.blogApp.BlogApplication.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.blogApp.BlogApplication.entity.Blogs;
import com.blogApp.BlogApplication.exception.ControllerException;
import com.blogApp.BlogApplication.exception.CustomException;
import com.blogApp.BlogApplication.service.BlogService;

@RestController
@RequestMapping("/api")
public class BlogController {
	@Autowired
	private BlogService blogService;

	// Get all blogs
	@GetMapping("/blogs")
	public ResponseEntity<List<Blogs>> getAllBlogs() {
		try {
			List<Blogs> getBlogsList = blogService.getAllBlogs();
			return ResponseEntity.status(HttpStatus.OK).body(getBlogsList);
		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<List<Blogs>>(HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("612", "Nothing is found to be returned");
			return new ResponseEntity<List<Blogs>>(HttpStatus.BAD_REQUEST);
		}
	}

	// // Create a blog
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/blogs")
	public ResponseEntity<?> addBlog(@RequestBody Blogs blog) {
		try {
			Blogs createBlog = blogService.addBlog(blog);
			return ResponseEntity.status(HttpStatus.CREATED).body(createBlog);
		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("611", "Something went wrong in the controller class ");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}

	}

	// // Check Whether a Blog is Published or not
	@GetMapping("/blogs/published={val}")
	public List<Blogs> publishedBlogs(@PathVariable("val") boolean val) {
		return blogService.publishedBlogs(val);
	}

	// // Get a Blog by ID
	@GetMapping("/blogs/:{id}")
	public ResponseEntity<?> publishedBlogs(@PathVariable("id") long id) {
		try {
			Blogs findblogId = blogService.findById(id);
			return ResponseEntity.status(HttpStatus.OK).body(findblogId);
		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("612", "Provided Parameters are not matching");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// // Update the Existing blog
	@PutMapping("/blogs/:{id}")
	public ResponseEntity<?> updateBlogs(@PathVariable("id") Long id, @RequestBody Blogs blog) {

		try {
			Blogs changeBlogs = blogService.updatedBlog(blog, id);
			return ResponseEntity.status(HttpStatus.OK).body(changeBlogs);

		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("615", "Something Went wrong Please Check your response");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("blogs/:{id}")
	public ResponseEntity<?> deleteBlog(@PathVariable("id") long id) {
		try {
			String DelFindblogId = blogService.deleteBlog(id);
			return ResponseEntity.status(HttpStatus.CREATED).body(DelFindblogId);
		} catch (CustomException e) {
			ControllerException ce = new ControllerException(e.getErrorType(), e.getErrorMessage());
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			ControllerException ce = new ControllerException("613", "Provided Parameters are not matching");
			return new ResponseEntity<ControllerException>(ce, HttpStatus.BAD_REQUEST);
		}
	}

	// // Delete All the blogs or clear the database
	@DeleteMapping("/blogs")
	public String deleteAllBlog() {
		blogService.ClearDatabase();
		return "Deleted All Blogs Successfully";
	}
}