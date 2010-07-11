package ar.com.natlehmann.cdcatalogue.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;

import ar.com.natlehmann.cdcatalogue.BeanLocator;
import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;

public class DaoBaseTest {
	
	protected Category category;
	protected Category otherCategory;
	protected Volume volume;
	protected Volume otherVolume;
	protected Resource resource;
	protected Resource secondResource;
	protected Resource otherResource;
	
	private CategoryDao categoryDao;
	private ResourceDao resourceDao;
	private VolumeDao volumeDao;
	
	public DaoBaseTest() {
		
		this.categoryDao = BeanLocator.instance().getCategoryDao();
		this.resourceDao = BeanLocator.instance().getResourceDao();
		this.volumeDao = BeanLocator.instance().getVolumeDao();
	}
	
	public ResourceDao getResourceDao() {
		return resourceDao;
	}
	
	public VolumeDao getVolumeDao() {
		return volumeDao;
	}
	
	public CategoryDao getCategoryDao() {
		return categoryDao;
	}	
	
	
	@Before
	public void onSetUpInTransaction() throws Exception {
		
		this.category = new Category(10,"CategoriaPrueba");
		this.otherCategory = new Category(11,"OtraCategoriaPrueba");
		
		this.volume = new Volume(10000,category,"VolumePrueba");
		this.otherVolume = new Volume(10001,otherCategory,"ZOtroVolumePrueba");
		
		this.resource = new Resource(volume,"ResourcePrueba",
				"este/es/el/path",".type","1234Kb",new Date(),"comentarios");
		
		this.secondResource = new Resource(volume,"ResourcePruebaNoEntra",
				"otro/path",".type","1234Kb",new Date(),"comentarios");
		
		Calendar otherDate = Calendar.getInstance();
		otherDate.add(Calendar.DAY_OF_MONTH, 1);
		this.otherResource = new Resource(otherVolume,"ZOtroResource",
				"zzz/otro/path",".zzz","9999Kb",otherDate.getTime(),"Zcomentarios");
		
		List<Resource> resources = new LinkedList<Resource>();
		resources.add(resource);
		resources.add(secondResource);
		volume.setResources(resources);
		
		List<Resource> otherResources = new LinkedList<Resource>();
		otherResources.add(otherResource);
		otherVolume.setResources(otherResources);
		
	}

}
