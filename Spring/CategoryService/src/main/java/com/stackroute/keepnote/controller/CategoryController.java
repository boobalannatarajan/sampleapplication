package com.stackroute.keepnote.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.service.CategoryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api
@CrossOrigin(origins = "*") // URL specification access
public class CategoryController {

	/*
	 * Autowiring should be implemented for the CategoryService. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword
	 */
	CategoryService categoryService;

	@Autowired
	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	/*
	 * Swagger
	 */
	@GetMapping("/")
	public String swaggerUi() {
		return "redirect:/swagger-ui.html";
	}

	/*
	 * Define a handler method which will create a category by reading the
	 * Serialized category object from request body and save the category in
	 * database. Please note that the careatorId has to be unique.This handler
	 * method should return any one of the status messages basis on different
	 * situations: 1. 201(CREATED - In case of successful creation of the category
	 * 2. 409(CONFLICT) - In case of duplicate categoryId
	 *
	 * 
	 * This handler method should map to the URL "/api/v1/category" using HTTP POST
	 * method".
	 */
	@ApiOperation(value = "Create Category")
	@PostMapping("/api/v1/category")
	public ResponseEntity<Category> createCategory(@RequestBody Category category) {
		category.setCategoryCreationDate(new Date());
		try {
			categoryService.createCategory(category);
			return new ResponseEntity<Category>(category, HttpStatus.CREATED);// For 201
		} catch (CategoryNotCreatedException e) {
			return new ResponseEntity<Category>(HttpStatus.CONFLICT);// For 409
		}
	}

	/*
	 * Define a handler method which will delete a category from a database.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category deleted successfully from
	 * database. 2. 404(NOT FOUND) - If the category with specified categoryId is
	 * not found.
	 * 
	 * This handler method should map to the URL "/api/v1/category/{id}" using HTTP
	 * Delete method" where "id" should be replaced by a valid categoryId without {}
	 */
	@ApiOperation(value = "Delete Category")
	@DeleteMapping("/api/v1/category/{id}")
	public ResponseEntity<Category> deleteCategory(@PathVariable("id") String categoryId) {
		Category delCategory = new Category();
		delCategory.setId(categoryId);
		try {
			categoryService.deleteCategory(categoryId);
			return new ResponseEntity<Category>(delCategory, HttpStatus.OK);// For 200
		} catch (CategoryDoesNoteExistsException e) {
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);// For 404
		}
	}

	/*
	 * Define a handler method which will update a specific category by reading the
	 * Serialized object from request body and save the updated category details in
	 * database. This handler method should return any one of the status messages
	 * basis on different situations: 1. 200(OK) - If the category updated
	 * successfully. 2. 404(NOT FOUND) - If the category with specified categoryId
	 * is not found. This handler method should map to the URL
	 * "/api/v1/category/{id}" using HTTP PUT method.
	 */
	@ApiOperation(value = "Update Category")
	@PutMapping("/api/v1/category/{id}")
	public ResponseEntity<Category> updateCategory(@PathVariable("id") String categoryId,
			@RequestBody Category category) {
		ResponseEntity<Category> output = null;
		Category resultCategory = new Category();
		resultCategory = categoryService.updateCategory(category, categoryId);
		if (resultCategory != null) {
			output = new ResponseEntity<Category>(resultCategory, HttpStatus.OK); // For 200
		} else {
			output = new ResponseEntity<Category>(HttpStatus.CONFLICT);// For 409
		}
		return output;
	}

	/*
	 * Define a handler method which will get us the category by a categoryId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category found successfully.
	 * 
	 * 
	 * This handler method should map to the URL "/api/v1/category/{id}" using HTTP
	 * GET method
	 */
	@ApiOperation(value = "Get Category By Category Id")
	@GetMapping("/api/v1/category/{id}")
	public ResponseEntity<Category> getCategory(@PathVariable("id") String categoryId) {
		ResponseEntity<Category> output = null;
		Category category = null;
		try {
			category = categoryService.getCategoryById(categoryId);
		} catch (CategoryNotFoundException e) {
		}
		if (category == null) {
			return new ResponseEntity<Category>(HttpStatus.NOT_FOUND); // For 404
		}
		output = new ResponseEntity<Category>(category, HttpStatus.OK); // For 200
		return output;
	}

	/*
	 * Define a handler method which will get us the category by a userId.
	 * 
	 * This handler method should return any one of the status messages basis on
	 * different situations: 1. 200(OK) - If the category found successfully.
	 * 
	 * 
	 * This handler method should map to the URL "/api/v1/category" using HTTP GET
	 * method
	 */
	@GetMapping("/api/v1/category/user/{userId}")
	public ResponseEntity<List<Category>> getCategoryByUser(@PathVariable("userId") String userId) {
		ResponseEntity<List<Category>> result = null;
		List<Category> category = categoryService.getAllCategoryByUserId(userId); // Get all category based on input id
		if (category == null) {
			return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);// For negative case
		}
		result = new ResponseEntity<List<Category>>(category, HttpStatus.OK); // For positive case
		return result;
	}

}
