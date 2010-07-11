package ar.com.natlehmann.cdcatalogue.dao;

import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.model.Category;

public interface CategoryDao {
	
	Category createCategory(Category category) throws DaoException;
	
	Category getCategory(Integer categoryId) throws DaoException;
	
	Category findCategory(String categoryName) throws DaoException;
	
	List<Category> getCategories() throws DaoException;
	
	/**
	 * Returns a list of ALL categories, each one containing its corresponding volumes.
	 * @return
	 * @throws DaoException
	 */
	List<Category> getCategoryVolumes() throws DaoException;

	void deleteCategory(Category category) throws DaoException;

	void updateCategory(Category category) throws DaoException;

}
