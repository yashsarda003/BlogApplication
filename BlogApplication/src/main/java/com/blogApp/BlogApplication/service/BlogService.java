package com.blogApp.BlogApplication.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogApp.BlogApplication.entity.Blogs;
import com.blogApp.BlogApplication.exception.CustomException;
import com.blogApp.BlogApplication.resource.BlogResource;

@Service
public class BlogService {
	@Autowired
	private BlogResource blogResource;

	// // Find all the blogs
	public List<Blogs> getAllBlogs() {
		List<Blogs> empList = blogResource.findAll();

		// Check if the list is empty
		if (empList.isEmpty()) {
			throw new CustomException("604", "The List Is empty no data to display..");
		}

		try {
			return empList;
		} catch (Exception e) {
			throw new CustomException("605",
					"Some Error occurred while fetching data from the database: " + e.getMessage());
		}
	}

	// // Add Blog to database
	public Blogs addBlog(Blogs blog) {
		if ((blog.getDescription() == null || blog.getTitle() == null) ||
				(blog.getDescription().trim().isEmpty() || blog.getTitle().trim().isEmpty())) {

			throw new CustomException("601", "Please provide both description and title for the blog.");
		}
		try {
			Blogs saveBlogs = blogResource.save(blog);
			return saveBlogs;
		} catch (IllegalArgumentException e) {
			throw new CustomException("602", "Please Check The Entered Fields.. : " + e.getMessage());
		}
	}

	public List<Blogs> publishedBlogs(Boolean val) {
		try {
			List<Blogs> isPublished = blogResource.findByPublished(val);
			return isPublished;

		} catch (Exception e) {
			throw new CustomException("605", "Please Give The corret input: " + e.getMessage());
		}
	}

	// // Find The blogs by Blog Id
	public Blogs findById(long id) {
		// // The JpaRepository interface exposes the existsById method, which checks if
		// an entity with the given id exists in the database:

		boolean exists = blogResource.existsById(id);
		if (exists) {
			try {
				Blogs findTheBlog = blogResource.findById(id).orElse(null);
				if (findTheBlog != null) {
					return findTheBlog;
				} else {
					throw new CustomException("607", "The Given Id returns a Null value");
				}
			}

			catch (Exception e) {
				throw new CustomException("607", "Some Error Occured : " + e.getMessage());
			}
		} else {

			throw new CustomException("607", "The Given ID Does not Exists ");
		}
	}

	// // Delete a blog by Id
	public String deleteBlog(long id) {
		// Check if the blog with the given ID exists
		boolean exists = blogResource.existsById(id);

		if (!exists) {
			throw new CustomException("607", "The Given ID Does not Exist");
		}

		// Fetch the existing blog
		Blogs findTheBlog = blogResource.findById(id).orElse(null);

		if (findTheBlog == null) {
			throw new CustomException("607", "The Given Id returns a Null value");
		}

		try {
			// Delete the blog
			blogResource.deleteById(id);
			return "Successfully Deleted";
		} catch (Exception e) {
			throw new CustomException("607", "Some Error Occurred: " + e.getMessage());
		}
	}

	// // Update a blog by given ID
	public Blogs updatedBlog(Blogs blog, long id) {
		// Validation: Check if description and title are provided
		if (blog.getDescription() == null || blog.getTitle() == null ||
				blog.getDescription().trim().isEmpty() || blog.getTitle().trim().isEmpty()) {
			throw new CustomException("601", "Please provide both description and title for the blog.");
		}

		// Check if the blog with the given ID exists
		boolean checkExistence = blogResource.existsById(id);
		if (!checkExistence) {
			// No blog found with the given ID
			throw new CustomException("608", "There is no blog found with the given ID");
		}

		// Fetch the existing blog
		Blogs existingBlog = blogResource.findById(id).orElse(null);

		if (existingBlog == null) {
			throw new CustomException("608", "NullPointerException: The provided blog returns a null value");
		}

		try {
			// Update the existing blog with new values
			existingBlog.setDescription(blog.getDescription());
			existingBlog.setTitle(blog.getTitle());
			existingBlog.setPublished(blog.isPublished());
			Blogs updatedBlog = blogResource.save(existingBlog);
			return updatedBlog;
		} catch (Exception e) {
			throw new CustomException("608",
					"Something went wrong while updating the blog with the given ID: " + e.getMessage());
		}
	}

	// Delete All Blogs
	public String ClearDatabase() {
		try {
			blogResource.deleteAll();
			return "All The Blogs have been Successfully deleted";

		} catch (Exception e) {
			throw new CustomException("609",
					"The Database is already Empty! make sure there exists some blogs...: " + e.getMessage());
		}
	}
}
