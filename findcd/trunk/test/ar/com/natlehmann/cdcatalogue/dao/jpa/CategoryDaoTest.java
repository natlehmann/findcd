package ar.com.natlehmann.cdcatalogue.dao.jpa;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.dao.CategoryDao;
import ar.com.natlehmann.cdcatalogue.dao.DaoBaseTest;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;

/**
 * To run these tests you must first run the script sql/InsertTestData.sql on the DB
 * @author natalia
 *
 */
public class CategoryDaoTest extends DaoBaseTest {
	
	private CategoryDao categoryDao;
	
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	
	@Test
	public void testFindByName() throws DaoException, SQLException {
		assertNotNull(this.categoryDao.findCategory("CategoriaPrueba"));
		assertNull(this.categoryDao.findCategory("ategoriaPrueba"));
	}
	
	@Test
	public void testPersistence() throws DaoException, SQLException {
		Category t = new Category("tirar");
		int initialCount = categoryDao.getCategories().size();
		
		Category o = this.categoryDao.createCategory(t);

		assertNotNull(o.getCategoryId());
		assertNotNull(this.categoryDao.findCategory("tirar"));
		assertTrue(categoryDao.getCategories().size() == initialCount + 1);

		this.categoryDao.deleteCategory(t);

		assertNull(this.categoryDao.findCategory("tirar"));
		assertTrue(categoryDao.getCategories().size() == initialCount);

	}
	
	@Test
	public void testGetCategoryVolumes() throws DaoException {
		List<Category> categories = this.categoryDao.getCategoryVolumes();
		assertNotNull(categories);
		assertFalse(categories.isEmpty());
		assertTrue(categories.size() >= 2);
		assertTrue(categories.contains(this.category));
		assertTrue(categories.contains(this.otherCategory));
		
		for (Category cat : categories) {
			if (cat.equals(this.category) || cat.equals(this.otherCategory)) {
				assertNotNull(cat.getVolumes());
				assertFalse(cat.getVolumes().isEmpty());
				assertTrue(cat.getVolumes().size() == 1);
			}
			
			if (cat.equals(this.category)) {
				assertTrue(cat.getVolumes().contains(this.volume));
			}
			if (cat.equals(this.otherCategory)) {
				assertTrue(cat.getVolumes().contains(this.otherVolume));
			}
		}
	}
	
	@Test
	public void testGetCategories() throws DaoException {
		
		List<Category> categories = this.categoryDao.getCategories();
		assertNotNull(categories);
		assertFalse(categories.isEmpty());
		assertTrue(categories.size() >= 2);
		assertTrue(categories.contains(this.category));
		assertTrue(categories.contains(this.otherCategory));
	}


}
