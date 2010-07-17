package ar.com.natlehmann.cdcatalogue.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.dao.CategoryDao;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.NotImplementedException;

public class CategoryDaoImpl implements CategoryDao {

	public Category createCategory(Category category) throws DaoException {
		
		try {
			
			Connection connection = DaoConnection.getInstance().getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
			"INSERT INTO Category (categoryName) VALUES (?)");
			
			statement.setString(1, category.getCategoryName());
			int rowsAffected = statement.executeUpdate();
			
			statement.close();
			
			if (rowsAffected == 1) {
				return this.getLastInserted();
				
			} else {
				throw new DaoException("Could not insert Category " + category);
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}

	}

	private Category getLastInserted() throws SQLException, DaoException {
		
		Connection connection = DaoConnection.getInstance().getConnection();
		
		Statement statement = connection.createStatement();
		
		ResultSet result = statement.executeQuery(
				"SELECT * FROM Category WHERE categoryId = (SELECT MAX(categoryId) FROM Category)");
		
		List<Category> results = this.convertResult(result);
		result.close();
		statement.close();
		
		if (!results.isEmpty()) {
			return results.iterator().next();
			
		} else {
			return null;
		}
	}

	public Category getCategory(Integer categoryId) throws DaoException {
		
		try {
			
			Connection connection = DaoConnection.getInstance().getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
			"SELECT * FROM Category WHERE categoryId = ?");
			
			statement.setInt(1, categoryId);
			ResultSet result = statement.executeQuery();
			
			List<Category> results = this.convertResult(result);
			result.close();
			statement.close();
			
			if (!results.isEmpty()) {
				return results.iterator().next();
				
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	private List<Category> convertResult(ResultSet resultSet) throws SQLException {
		
		List<Category> results = new LinkedList<Category>();
		
		while (resultSet.next()) {
			Category category = new Category();
			category.setCategoryId(resultSet.getInt("categoryId"));
			category.setCategoryName(resultSet.getString("categoryName"));
			
			results.add(category);
		}
		
		return results;
	}

	public Category findCategory(String categoryName) throws DaoException {		

		try {
			
			Connection connection = DaoConnection.getInstance().getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
			"SELECT * FROM Category WHERE categoryName = ?");
			
			statement.setString(1, categoryName);
			ResultSet result = statement.executeQuery();
			
			List<Category> results = this.convertResult(result);
			result.close();
			statement.close();
			
			if (!results.isEmpty()) {
				return results.iterator().next();
				
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	@Override
	public List<Category> getCategoryVolumes() throws DaoException {
		throw new NotImplementedException("Not implemented");
	}

	@Override
	public List<Category> getCategories() throws DaoException {
		throw new NotImplementedException("Not implemented");
	}

	@Override
	public void deleteCategory(Category category) throws DaoException {
		throw new NotImplementedException("Not implemented");		
	}

	@Override
	public void updateCategory(Category category) throws DaoException {
		throw new NotImplementedException("Not implemented");		
		
	}
	
	
//	public static void main(String[] args) {
//		CategoryDaoImpl dao = new CategoryDaoImpl();
//		
//		Category category = new Category();
//		category.setCategoryName("nombre");
//		
//		try {
//			dao.createCategory(category);
//			
//			Category cat = dao.getCategory(1);
//			System.out.println(cat);
//			
//			
//		} catch (DaoException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}
