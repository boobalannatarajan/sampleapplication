package com.stackroute.keepnote.service;

import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.stackroute.keepnote.exception.CategoryDoesNoteExistsException;
import com.stackroute.keepnote.exception.CategoryNotCreatedException;
import com.stackroute.keepnote.exception.CategoryNotFoundException;
import com.stackroute.keepnote.model.Category;
import com.stackroute.keepnote.repository.CategoryRepository;

/*
* Service classes are used here to implement additional business logic/validation 
* This class has to be annotated with @Service annotation.
* @Service - It is a specialization of the component annotation. It doesn't currently 
* provide any additional behavior over the @Component annotation, but it's a good idea 
* to use @Service over @Component in service-layer classes because it specifies intent 
* better. Additionally, tool support and additional behavior might rely on it in the 
* future.
* */
@Service
public class CategoryServiceImpl implements CategoryService {
	/*
	 * Autowiring should be implemented for the CategoryRepository. (Use
	 * Constructor-based autowiring) Please note that we should not create any
	 * object using the new keyword.
	 */
	CategoryRepository categoryRepository;

	@Autowired
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	/*
	 * This method should be used to save a new category.Call the corresponding
	 * method of Respository interface.
	 */
	public Category createCategory(Category category) throws CategoryNotCreatedException {
		Category catObj = new Category();
		if (category != null) {
			catObj = categoryRepository.insert(category);// it will insert category
		}
		if (catObj == null || catObj.equals(null)) {
			throw new CategoryNotCreatedException("Category Not Created"); // For negative case
		}
		return catObj;
	}

	/*
	 * This method should be used to delete an existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public boolean deleteCategory(String categoryId) throws CategoryDoesNoteExistsException {
		boolean isBooStatus = false;
		Category getCategory = new Category();
		getCategory = categoryRepository.findById(categoryId).get();// First find id and then delete operation
		if (getCategory != null) {
			categoryRepository.delete(getCategory);
			isBooStatus = true;
		}
		if (getCategory == null || getCategory.equals(null)) {
			throw new CategoryDoesNoteExistsException("Category Does Not Exists"); // if not exist case
		}
		return isBooStatus;
	}

	/*
	 * This method should be used to update a existing category.Call the
	 * corresponding method of Respository interface.
	 */
	public Category updateCategory(Category category, String categoryId) {
		Category updCategory = new Category();
		Category existingCategory = null;
		try {
			existingCategory = categoryRepository.findById(categoryId).get(); // First find id and then update operation
		} catch (NoSuchElementException e) {
			existingCategory = null;
		}
		if (existingCategory != null) {
			category.setCategoryCreationDate(existingCategory.getCategoryCreationDate());
			category.setCategoryCreatedBy(existingCategory.getCategoryCreatedBy());
			categoryRepository.save(category); // Then save now
			updCategory = category;
		}
		return updCategory;
	}

	/*
	 * This method should be used to get a category by categoryId.Call the
	 * corresponding method of Respository interface.
	 */
	public Category getCategoryById(String categoryId) throws CategoryNotFoundException {
		Category getCategory = new Category();
		if (!categoryId.isEmpty()) {// Check first id is there or not
			try {
				getCategory = categoryRepository.findById(categoryId).get();// Get ID
			} catch (NoSuchElementException e) {
				getCategory = null;
			}
		}
		if (getCategory == null || getCategory.equals(null)) {
			throw new CategoryNotFoundException("Category Not Found");// For -ve case
		}
		return getCategory;
	}

	/*
	 * This method should be used to get a category by userId.Call the corresponding
	 * method of Respository interface.
	 */
	public List<Category> getAllCategoryByUserId(String userId) {
		return categoryRepository.findAllCategoryByCategoryCreatedBy(userId);// Get All id's
	}
}