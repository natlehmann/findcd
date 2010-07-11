package ar.com.natlehmann.cdcatalogue.dao.jpa;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.dao.CategoryDao;
import ar.com.natlehmann.cdcatalogue.dao.DaoBaseTest;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.ResourceDao;
import ar.com.natlehmann.cdcatalogue.dao.SearchField;

/**
 * To run these tests you must first run the script sql/InsertTestData.sql on the DB
 * @author natalia
 *
 */
public class ResourceCategoryDaoTest extends DaoBaseTest {
	
	private ResourceDao resourceDao;
	private CategoryDao categoryDao;
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ResourceCategoryDaoTest.class);
	
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
	
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}

	@Test
	public void testUpdateCategoryNameInResources() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, this.category.getCategoryName()));
		List<Resource> resources = resourceDao.getResources(parameters);

		checkIfPresent(resources);
		
		String oldCategoryName = this.category.getCategoryName();
		
		this.category.setCategoryName("nuevo nombre prueba");
		categoryDao.updateCategory(category);
		
		Category updatedCategory = categoryDao.getCategory(this.category.getCategoryId());
		assertTrue(updatedCategory.getCategoryName().equals("nuevo nombre prueba"));
		
		parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "nuevo nombre prueba"));
		resources = resourceDao.getResources(parameters);
		
		checkIfPresent(resources);
		
		for (Resource resource :  resources) {
			assertEquals("nuevo nombre prueba", resource.getVolume().getCategory().getCategoryName());
		}
		
		
		this.category.setCategoryName(oldCategoryName);
		categoryDao.updateCategory(category);
		
	}

	private void checkIfPresent(List<Resource> resources) {
		boolean found1 = false;
		boolean found2 = false;
		
		for (Resource resource : resources) {
			if (resource.getResourceName().equals(this.resource.getResourceName())) {
				found1 = true;
			}
			if (resource.getResourceName().equals(this.secondResource.getResourceName())) {
				found2 = true;
			}
		}
		assertTrue(found1 && found2);
	}
}
