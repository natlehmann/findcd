package ar.com.natlehmann.cdcatalogue.dao.jpa;

import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.dao.DaoBaseTest;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.OrderField;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.ResourceDao;
import ar.com.natlehmann.cdcatalogue.dao.SearchField;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy.Direction;

/**
 * To run these tests you must first run the script sql/InsertTestData.sql on the DB
 * @author natalia
 *
 */
public class ResourceDaoTest extends DaoBaseTest {
	
	private ResourceDao resourceDao;

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(ResourceDaoTest.class);
	
	
	
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}

	
	@Test
	public void testGetResourcesOneItem() throws DaoException {		
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.VOLUME_NAME, this.volume.getVolumeName()));
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, this.category.getCategoryName()));
		parameters.add(new Parameter(SearchField.RESOURCE_COMMENTS, this.resource.getComments()));
		parameters.add(new Parameter(SearchField.RESOURCE_NAME, this.resource.getResourceName()));
		parameters.add(new Parameter(SearchField.RESOURCE_PATH, this.resource.getPath()));
		parameters.add(new Parameter(SearchField.RESOURCE_TYPE, this.resource.getResourceType()));
		
		List<Resource> results = this.resourceDao.getResources(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay resultados", results.isEmpty());
		assertTrue("Mas de un resultado", results.size() == 1);
		assertEquals("No es la categoria", this.category, results.get(0).getVolume().getCategory());
		assertEquals("No es el recurso", this.resource.getResourceName(), 
				results.get(0).getResourceName());
		assertTrue("Mas de un recurso", results.size() == 1);
		
	}
	
	@Test
	public void testGetResources() throws DaoException {		
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.VOLUME_NAME, "olumePrue"));
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "ategoriaPrue"));
		parameters.add(new Parameter(SearchField.RESOURCE_COMMENTS, "comen"));
		parameters.add(new Parameter(SearchField.RESOURCE_NAME, "sourcePrue"));
		parameters.add(new Parameter(SearchField.RESOURCE_PATH, "es/el/path"));
		parameters.add(new Parameter(SearchField.RESOURCE_TYPE, "type"));
		
		List<Resource> results = this.resourceDao.getResources(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("Los resultados no contienen al volumen indicado", 
				this.resourceIsPresent(results,this.resource));
		assertFalse("Los resultados contienen al volumen no esperado", 
				this.resourceIsPresent(results,this.secondResource));
	}
	
	private boolean resourceIsPresent(List<Resource> results, Resource resource) {
		
		boolean isPresent = false;
		Iterator<Resource> it = results.iterator();
		
		while(!isPresent && it.hasNext()) {
			isPresent = resource.getResourceName().equals(
					it.next().getResourceName());
		}
		
		return isPresent;
	}

	@Test
	public void testGetResourcesByCategory() throws DaoException {

		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "otroNombre"));
		
		List<Resource> results = this.resourceDao.getResources(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertTrue("Hay resultados invalidos", results.isEmpty());
		
		parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "CategoriaPrueba"));
		results = this.resourceDao.getResources(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("Los resultados no contienen al volumen indicado", 
				this.resourceIsPresent(results,this.resource));
	}
	
	@Test
	public void testGetResourcesByResourceName() throws DaoException {
			
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_NAME, "otroNombre"));
		
		List<Resource> results = this.resourceDao.getResources(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertTrue("Hay resultados invalidos", results.isEmpty());
		
		parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_NAME, "ResourcePrueba"));
		results = this.resourceDao.getResources(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("Los resultados no contienen al volumen indicado", 
				this.resourceIsPresent(results,this.resource));
	}
	
	@Test
	public void testGetResourcesDateFrom() throws DaoException {
		
		Date date = new Date(0);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 1);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date futuro = cal.getTime();
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_FROM, futuro));
		
		List<Resource> results = this.resourceDao.getResources(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertTrue("Hay resultados invalidos", results.isEmpty());
		
		parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_FROM, date));
		results = this.resourceDao.getResources(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("Los resultados no contienen al volumen indicado", 
				this.resourceIsPresent(results,this.resource));
		assertTrue("Los resultados no contienen al volumen indicado", 
				this.resourceIsPresent(results,this.otherResource));
	}
	
	@Test
	public void testGetResourcesDateFromTo() throws DaoException {
		
		Date date = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -1);
		Date past = cal.getTime();
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_TO, past));
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_FROM, date));
		
		List<Resource> results = this.resourceDao.getResources(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertTrue("Hay resultados invalidos", results.isEmpty());
		
		parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_TO, date));
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_FROM, new Date(0)));
		
		results = this.resourceDao.getResources(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("Los resultados no contienen al volumen indicado", 
				this.resourceIsPresent(results,this.resource));
	}
	
	
	@Test
	public void testGetResourcesOrderedByCategoryName() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Resource> results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.CATEGORY_NAME, Direction.ASC), new Page(1));
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("No hay suficientes resultados", results.size() >= 2);
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) < 
				this.getIndexFromName(results, this.otherResource));
		
		results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.CATEGORY_NAME, Direction.DESC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) > 
				this.getIndexFromName(results, this.otherResource));
		
	}
	
	
	private int getIndexFromName(List<Resource> results, Resource resource) {
		
		int index = -1;
		int iteratorIndex = 0;
		Iterator<Resource> it = results.iterator();
		
		while (index < 0 && it.hasNext()) {
			if (it.next().getResourceName().equals(resource.getResourceName())) {
				index = iteratorIndex;
			}
			iteratorIndex++;
		}
		
		return index;
	}

	@Test
	public void testGetResourcesOrderedByVolumeName() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Resource> results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.VOLUME_NAME, Direction.ASC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) 
				< this.getIndexFromName(results, this.otherResource));
		
		results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.VOLUME_NAME, Direction.DESC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) 
				> this.getIndexFromName(results, this.otherResource));
		
	}
	
	
	@Test
	public void testGetResourcesOrderedByResourceName() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Resource> results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.RESOURCE_NAME, Direction.ASC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) 
				< this.getIndexFromName(results, this.otherResource));
		
		results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.RESOURCE_NAME, Direction.DESC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) 
				> this.getIndexFromName(results, this.otherResource));
		
	}
	
	
	@Test
	public void testGetResourcesOrderedByResourcePath() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Resource> results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.RESOURCE_PATH, Direction.ASC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) 
				< this.getIndexFromName(results, this.otherResource));
		
		results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.RESOURCE_PATH, Direction.DESC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) 
				> this.getIndexFromName(results, this.otherResource));
		
	}
	
	
	@Test
	public void testGetResourcesOrderedByResourceType() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Resource> results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.RESOURCE_TYPE, Direction.ASC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) 
				< this.getIndexFromName(results, this.otherResource));
		
		results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.RESOURCE_TYPE, Direction.DESC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) 
				> this.getIndexFromName(results, this.otherResource));
		
	}
	
	
	@Test
	public void testGetResourcesOrderedByResourceDate() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Resource> results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.RESOURCE_MODIFIED_DATE, Direction.ASC), new Page(1));
		
		assertNotNull("Resultados nulos", results);
		assertFalse("Resultados vacios", results.isEmpty());
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) 
				< this.getIndexFromName(results, this.otherResource));
		
		results = this.resourceDao.getResources(parameters, new OrderBy(
				OrderField.RESOURCE_MODIFIED_DATE, Direction.DESC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				this.getIndexFromName(results, this.resource) 
				> this.getIndexFromName(results, this.otherResource));
		
	}
	


}
