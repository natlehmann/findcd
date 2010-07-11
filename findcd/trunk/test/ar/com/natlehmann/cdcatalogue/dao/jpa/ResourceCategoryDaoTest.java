package ar.com.natlehmann.cdcatalogue.dao.jpa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.dao.DaoBaseTest;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.SearchField;

/**
 * To run these tests you must first run the script sql/InsertTestData.sql on the DB
 * @author natalia
 *
 */
public class ResourceCategoryDaoTest extends DaoBaseTest {
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ResourceCategoryDaoTest.class);
	

	@Test
	public void testUpdateCategoryNameInResources() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, this.category.getCategoryName()));
		List<Resource> resources = this.getResourceDao().getResources(parameters);

		checkIfPresent(resources);
		
		String oldCategoryName = this.category.getCategoryName();
		
		this.category.setCategoryName("nuevo nombre prueba");
		this.getCategoryDao().updateCategory(category);
		
		Category updatedCategory = this.getCategoryDao().getCategory(this.category.getCategoryId());
		assertTrue(updatedCategory.getCategoryName().equals("nuevo nombre prueba"));
		
		parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "nuevo nombre prueba"));
		resources = this.getResourceDao().getResources(parameters);
		
		checkIfPresent(resources);
		
		for (Resource resource :  resources) {
			assertEquals("nuevo nombre prueba", resource.getVolume().getCategory().getCategoryName());
		}
		
		
		this.category.setCategoryName(oldCategoryName);
		this.getCategoryDao().updateCategory(category);
		
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
