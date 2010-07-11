package ar.com.natlehmann.cdcatalogue.dao.jpa;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;
import ar.com.natlehmann.cdcatalogue.dao.CategoryDao;
import ar.com.natlehmann.cdcatalogue.dao.DaoBaseTest;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.OrderField;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.SearchField;
import ar.com.natlehmann.cdcatalogue.dao.VolumeDao;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy.Direction;


/**
 * To run these tests you must first run the script sql/InsertTestData.sql on the DB
 * @author natalia
 *
 */
public class VolumeDaoTest extends DaoBaseTest {
	
	private VolumeDao volumeDao;
	
	private CategoryDao categoryDao;
	
	
	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(VolumeDaoTest.class);
	
	
	public void setvolumeDao(VolumeDao volumeDao) {
		this.volumeDao = volumeDao;
	}
	
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	@Test
	public void testGetVolumesOneItem() throws DaoException {		
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.VOLUME_NAME, this.volume.getVolumeName()));
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, this.category.getCategoryName()));
		parameters.add(new Parameter(SearchField.RESOURCE_COMMENTS, this.resource.getComments()));
		parameters.add(new Parameter(SearchField.RESOURCE_NAME, this.resource.getResourceName()));
		parameters.add(new Parameter(SearchField.RESOURCE_PATH, this.resource.getPath()));
		parameters.add(new Parameter(SearchField.RESOURCE_TYPE, this.resource.getResourceType()));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay resultados", results.isEmpty());
		assertTrue("Mas de un resultado", results.size() == 1);
		assertEquals("No es la categoria", this.category.getCategoryName(), 
				results.get(0).getCategory().getCategoryName());
		assertEquals("No es el volumen", this.volume.getVolumeName(), 
				results.get(0).getVolumeName());
		assertTrue("Mas de un recurso", results.get(0).getResources().size() == 1);
		assertEquals("No es el recurso", this.resource.getResourceName(), 
				results.get(0).getResources().iterator().next().getResourceName());
		
	}
	
	@Test
	public void testGetVolumes() throws DaoException {		
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.VOLUME_NAME, "olumePrue"));
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "ategoriaPrue"));
		parameters.add(new Parameter(SearchField.RESOURCE_COMMENTS, "comen"));
		parameters.add(new Parameter(SearchField.RESOURCE_NAME, "sourcePrue"));
		parameters.add(new Parameter(SearchField.RESOURCE_PATH, "es/el/path"));
		parameters.add(new Parameter(SearchField.RESOURCE_TYPE, "type"));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		
		assertTrue("Los resultados no contienen al volumen indicado", results.contains(volume));
	}
	
	@Test
	public void testGetVolumesCategory() throws DaoException {

		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "otroNombre"));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertTrue("Hay resultados invalidos", results.isEmpty());
		
		parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "CategoriaPrueba"));
		results = this.volumeDao.getVolumes(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("Los resultados no contienen al volumen indicado", results.contains(volume));
	}
	
	@Test
	public void testGetVolumesResourceName() throws DaoException {
			
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_NAME, "otroNombre"));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertTrue("Hay resultados invalidos", results.isEmpty());
		
		parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_NAME, "ResourcePrueba"));
		results = this.volumeDao.getVolumes(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("Los resultados no contienen al volumen indicado", results.contains(volume));
	}
	
	@Test
	public void testGetVolumesDateFrom() throws DaoException {
		
		Date date = new Date(0);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, 1);
		cal.add(Calendar.DAY_OF_MONTH, 1);
		Date futuro = cal.getTime();
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_FROM, futuro));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertTrue("Hay resultados invalidos", results.isEmpty());
		
		parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_FROM, date));
		results = this.volumeDao.getVolumes(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("Los resultados no contienen al volumen indicado", results.contains(volume));
		assertTrue("Los resultados no contienen al volumen indicado", results.contains(otherVolume));
	}
	
	@Test
	public void testGetVolumesDateFromTo() throws DaoException {
		
		Date date = new Date();
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, -1);
		Date past = cal.getTime();
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_TO, past));
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_FROM, date));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertTrue("Hay resultados invalidos", results.isEmpty());
		
		parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_TO, new Date()));
		parameters.add(new Parameter(SearchField.RESOURCE_MODIFIED_DATE_FROM, new Date(0)));
		
		results = this.volumeDao.getVolumes(parameters);
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("Los resultados no contienen al volumen indicado", results.contains(volume));
	}
	
	
	@Test
	public void testGetVolumesOrderedByCategoryName() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.CATEGORY_NAME, Direction.ASC));
		
		assertNotNull("Resultados nulos", results);
		assertFalse("No hay ningun resultado", results.isEmpty());
		assertTrue("No hay suficientes resultados", results.size() >= 2);
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) < results.indexOf(this.otherVolume));
		
		results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.CATEGORY_NAME, Direction.DESC));
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) > results.indexOf(this.otherVolume));
		
	}
	
	
	@Test
	public void testGetVolumesOrderedByVolumeName() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.VOLUME_NAME, Direction.ASC));
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) < results.indexOf(this.otherVolume));
		
		results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.VOLUME_NAME, Direction.DESC));
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) > results.indexOf(this.otherVolume));
		
	}
	
	
	@Test
	public void testGetVolumesOrderedByResourceName() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.RESOURCE_NAME, Direction.ASC));
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) < results.indexOf(this.otherVolume));
		
		results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.RESOURCE_NAME, Direction.DESC));
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) > results.indexOf(this.otherVolume));
		
	}
	
	
	@Test
	public void testGetVolumesOrderedByResourcePath() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "Prueba"));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.RESOURCE_PATH, Direction.ASC), new Page(1));
		
		assertNotNull("resultados nulos", results);
		assertFalse("resultados vacios", results.isEmpty());
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) < results.indexOf(this.otherVolume));
		
		results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.RESOURCE_PATH, Direction.DESC));
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) > results.indexOf(this.otherVolume));
		
	}
	
	@Test
	public void testLikeSearch() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "Prueba"));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.RESOURCE_PATH, Direction.ASC), new Page(1));
		
		System.out.println("RESULTADOS = " + results);
		
		assertNotNull("resultados nulos", results);
		assertFalse("resultados vacios", results.isEmpty());
		
		
	}
	
	
	@Test
	public void testGetVolumesOrderedByResourceType() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.RESOURCE_TYPE, Direction.ASC), new Page(1));
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) < results.indexOf(this.otherVolume));
		
		results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.RESOURCE_TYPE, Direction.DESC));
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) > results.indexOf(this.otherVolume));
		
	}
	
	
	@Test
	public void testGetVolumesOrderedByResourceDate() throws DaoException {
		
		List<Parameter> parameters = new LinkedList<Parameter>();
		parameters.add(new Parameter(SearchField.CATEGORY_NAME, "prueba"));
		
		List<Volume> results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.RESOURCE_MODIFIED_DATE, Direction.ASC), new Page(1));
		
		assertNotNull("resultados nulos", results);
		assertFalse("resultados vacios", results.isEmpty());
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) < results.indexOf(this.otherVolume));
		
		results = this.volumeDao.getVolumes(parameters, new OrderBy(
				OrderField.RESOURCE_MODIFIED_DATE, Direction.DESC));
		
		assertTrue("El ordenamiento no es valido", 
				results.indexOf(this.volume) > results.indexOf(this.otherVolume));
		
	}
	
	
	@Test
	public void testGetVolumesPaginated() throws DaoException {
		
		List<Volume> results = this.volumeDao.getVolumes(new Page(1));
		
		assertNotNull("Resultados nulos", results);
		assertFalse("Resultados vacios", results.isEmpty());
		assertTrue("Esta mal la paginacion", results.size() <= Page.RESULTS_PER_PAGE);
		
		results = this.volumeDao.getVolumes(new Page(2));
		
		assertTrue("Esta mal la paginacion", results.size() <= Page.RESULTS_PER_PAGE);
	}
	
	@Test
	public void testGetVolumesByCategory() throws DaoException {
		
		List<Volume> volumes = this.volumeDao.getVolumes(this.category);
		assertNotNull(volumes);
		assertFalse(volumes.isEmpty());
		assertTrue(volumes.size() == 1);
		assertTrue(volumes.contains(this.volume));
		
		volumes = this.volumeDao.getVolumes(this.otherCategory);
		assertNotNull(volumes);
		assertFalse(volumes.isEmpty());
		assertTrue(volumes.size() == 1);
		assertTrue(volumes.contains(this.otherVolume));
	}
	
	@Test
	public void testCascade() throws DaoException {
		
		Volume vol = new Volume("tirar2");
		vol.setResources(new LinkedList<Resource>());
		vol.getResources().add(new Resource(vol,"resourceName", "/path"));
		
		vol = volumeDao.createVolume(vol);
		assertNotNull(vol.getVolumeId());
		
		Volume found = volumeDao.findVolume("tirar2");
		assertNotNull(found);
		assertNotNull(found.getResources());
		assertFalse(found.getResources().isEmpty());
		assertTrue(found.getResources().iterator().next().getResourceName().equals("resourceName"));
		
		volumeDao.deleteVolume(found);
		
		found = volumeDao.findVolume("tirar2");
		assertNull(found);
	}
	
	
	@Test
	public void testUpdateOneVolume() throws DaoException {
		
		Volume vol = new Volume("tirar2");
		vol = volumeDao.createVolume(vol);
		
		Volume found = volumeDao.findVolume("tirar2");
		assertNotNull(found);
		
		found.setVolumeName("tirar3");
		volumeDao.updateVolume(found);
		
		Volume foundUpdated = volumeDao.findVolume("tirar3");
		assertNotNull(foundUpdated);
		
		foundUpdated = volumeDao.findVolume("tirar2");
		assertNull(foundUpdated);
		
		volumeDao.deleteVolume(found);		
	}
	
	@Test
	public void testUpdateOneVolumeCascade() throws DaoException {
		
		Category category1 = this.categoryDao.createCategory(new Category("tirar4"));
		Category category2 = this.categoryDao.createCategory(new Category("tirar5"));
		
		Volume vol = new Volume(category1, "tirar2");
		vol = volumeDao.createVolume(vol);
		
		Volume found = volumeDao.findVolume("tirar2");
		assertNotNull(found);
		assertTrue(found.getCategory().getCategoryName().equals("tirar4"));
		
		found.setCategory(category2);
		volumeDao.updateVolume(found);
		
		Volume foundUpdated = volumeDao.findVolume("tirar2");
		assertNotNull(foundUpdated);
		assertTrue(foundUpdated.getCategory().getCategoryName().equals("tirar5"));
		
		volumeDao.deleteVolume(foundUpdated);	
		categoryDao.deleteCategory(category1);
		categoryDao.deleteCategory(category2);
	}
	
	@Test
	public void testUpdateVolumes() throws DaoException {
		
		Volume vol1 = new Volume("tirar2");
		Volume vol2 = new Volume("tirar3");
		
		List<Volume> volumes = new LinkedList<Volume>();
		volumes.add(vol1);
		volumes.add(vol2);
		
		for (Volume volume : volumes) {
			volumeDao.createVolume(volume);
		}
		
		vol1.setVolumeName("tirar4");
		vol2.setVolumeName("tirar5");
		
		volumeDao.updateVolumes(volumes);
		
		Volume foundUpdated = volumeDao.findVolume("tirar4");
		assertNotNull(foundUpdated);
		volumeDao.findVolume("tirar5");
		assertNotNull(foundUpdated);
		
		foundUpdated = volumeDao.findVolume("tirar2");
		assertNull(foundUpdated);
		foundUpdated = volumeDao.findVolume("tirar3");
		assertNull(foundUpdated);
		
		volumeDao.deleteVolume(volumeDao.findVolume("tirar4"));	
		volumeDao.deleteVolume(volumeDao.findVolume("tirar5"));
	}
	
	@Test
	public void testUpdateVolumesCascade() throws DaoException {
		
		Category category1 = this.categoryDao.createCategory(new Category("tirar4"));
		Category category2 = this.categoryDao.createCategory(new Category("tirar5"));
		
		Volume vol1 = new Volume(category1, "tirar2");
		vol1 = volumeDao.createVolume(vol1);
		
		Volume vol2 = new Volume(category1, "tirar3");
		vol2 = volumeDao.createVolume(vol2);
		
		Volume found = volumeDao.findVolume("tirar2");
		assertNotNull(found);
		assertTrue(found.getCategory().getCategoryName().equals("tirar4"));
		found = volumeDao.findVolume("tirar3");
		assertNotNull(found);
		assertTrue(found.getCategory().getCategoryName().equals("tirar4"));
		
		List<Volume> volumes = new LinkedList<Volume>();
		volumes.add(vol1);
		volumes.add(vol2);
		
		vol1.setCategory(category2);
		vol2.setCategory(category2);
		
		volumeDao.updateVolumes(volumes);
		
		found = volumeDao.findVolume("tirar2");
		assertNotNull(found);
		assertTrue(found.getCategory().getCategoryName().equals("tirar5"));
		found = volumeDao.findVolume("tirar3");
		assertNotNull(found);
		assertTrue(found.getCategory().getCategoryName().equals("tirar5"));
		
		categoryDao.deleteCategory(category1);
		
		volumeDao.deleteVolume(vol1);
		volumeDao.deleteVolume(vol2);
		categoryDao.deleteCategory(category2);
	}

}
