package ar.com.natlehmann.cdcatalogue.dao.jpa;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.dao.DaoBaseTest;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;

/**
 * To run these tests you must first run the script sql/InsertTestData.sql on the DB
 * @author natalia
 *
 */
public class CategoryDaoTest extends DaoBaseTest {

	
	@Test
	public void testFindByName() throws DaoException, SQLException {
		assertNotNull(this.getCategoryDao().findCategory("CategoriaPrueba"));
		assertNull(this.getCategoryDao().findCategory("ategoriaPrueba"));
	}
	
	@Test
	public void testPersistence() throws DaoException, SQLException {
		Category t = new Category("tirar");
		int initialCount = this.getCategoryDao().getCategories().size();
		
		Category o = this.getCategoryDao().createCategory(t);

		assertNotNull(o.getCategoryId());
		assertNotNull(this.getCategoryDao().findCategory("tirar"));
		assertTrue(this.getCategoryDao().getCategories().size() == initialCount + 1);

		this.getCategoryDao().deleteCategory(t);

		assertNull(this.getCategoryDao().findCategory("tirar"));
		assertTrue(this.getCategoryDao().getCategories().size() == initialCount);

	}
	
	@Test
	public void testGetCategoryVolumes() throws DaoException {
		List<Category> categories = this.getCategoryDao().getCategoryVolumes();
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
		
		List<Category> categories = this.getCategoryDao().getCategories();
		assertNotNull(categories);
		assertFalse(categories.isEmpty());
		assertTrue(categories.size() >= 2);
		assertTrue(categories.contains(this.category));
		assertTrue(categories.contains(this.otherCategory));
	}


}
