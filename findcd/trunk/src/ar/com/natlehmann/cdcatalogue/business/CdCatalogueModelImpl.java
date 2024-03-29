package ar.com.natlehmann.cdcatalogue.business;

import java.io.File;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.natlehmann.cdcatalogue.Configuration;
import ar.com.natlehmann.cdcatalogue.business.exception.CdCatalogueException;
import ar.com.natlehmann.cdcatalogue.business.exception.DuplicateNameException;
import ar.com.natlehmann.cdcatalogue.business.exception.InvalidNameException;
import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;
import ar.com.natlehmann.cdcatalogue.business.notification.CategoryDeletedNotification;
import ar.com.natlehmann.cdcatalogue.business.notification.CategoryUpdatedNotification;
import ar.com.natlehmann.cdcatalogue.business.notification.NewCategoryNotification;
import ar.com.natlehmann.cdcatalogue.business.notification.NewVolumeNotification;
import ar.com.natlehmann.cdcatalogue.business.notification.NotificationCommand;
import ar.com.natlehmann.cdcatalogue.business.notification.VolumeDeletedNotification;
import ar.com.natlehmann.cdcatalogue.business.notification.VolumeUpdatedNotification;
import ar.com.natlehmann.cdcatalogue.business.notification.VolumesUpdatedNotification;
import ar.com.natlehmann.cdcatalogue.dao.CategoryDao;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.ResourceDao;
import ar.com.natlehmann.cdcatalogue.dao.VolumeDao;
import ar.com.natlehmann.cdcatalogue.dao.jpa.DaoResources;

public class CdCatalogueModelImpl implements CdCatalogueBusinessModel {
	
	private static Log log = LogFactory.getLog(CdCatalogueModelImpl.class);
	
	private VolumeDao volumeDao;
	private ResourceDao resourceDao;
	private CategoryDao categoryDao;

	private List<CdCatalogueModelObserver> observers = new LinkedList<CdCatalogueModelObserver>();
	
	public void addObserver(CdCatalogueModelObserver observer) {
		this.observers.add(observer);
	}

	public VolumeDao getVolumeDao() {
		return volumeDao;
	}

	public void setVolumeDao(VolumeDao volumeDao) {
		this.volumeDao = volumeDao;
	}
	
	public ResourceDao getResourceDao() {
		return resourceDao;
	}
	
	public void setResourceDao(ResourceDao resourceDao) {
		this.resourceDao = resourceDao;
	}
	
	public CategoryDao getCategoryDao() {
		return categoryDao;
	}
	
	public void setCategoryDao(CategoryDao categoryDao) {
		this.categoryDao = categoryDao;
	}
	
	public List<Volume> getVolumes(List<Parameter> parameters, OrderBy orderField, Page page) {
		
		List<Volume> results = new LinkedList<Volume>();
		
		try {
			results = this.volumeDao.getVolumes(parameters, orderField, page);
			
		} catch (DaoException e) {
			log.error("Could not retrieve volumes.",e);
		}
		
		return results;
	}

	@Override
	public List<Resource> getResources(List<Parameter> parameters,
			OrderBy orderField, Page page) {
		
		List<Resource> results = new LinkedList<Resource>();
		
		try {
			results = this.resourceDao.getResources(parameters, orderField, page);
			
		} catch (DaoException e) {
			log.error("Could not retrieve resources.",e);
		}
		
		return results;
	}

	@Override
	public long getResourceCount(List<Parameter> searchParameters) {

		long count = 0;
		
		try {
			count = this.resourceDao.getResourceCount(searchParameters);
			
		} catch (DaoException e) {
			log.error("Could not retrieve resource count.",e);
		}
		
		return count;
	}

	@Override
	public List<Category> getCategories() {
		
		List<Category> categories = new LinkedList<Category>();
		
		try {
			categories = this.categoryDao.getCategories();
			
			if (categories.isEmpty()) {				
				categories.add(this.getDefaultCategory());
			}
		
		} catch (Exception e) {
			log.error("Could not retrieve categories.",e);
		}
		
		return categories;
	}

	@Override
	public List<Volume> getVolumesByCategory(Category category) {
		
		List<Volume> volumes = new LinkedList<Volume>();
		
		try {
			volumes = this.volumeDao.getVolumes(category);
		
		} catch (DaoException e){
			log.error("Could not retrieve volumes.",e);
		}
		
		return volumes;
	}

	@Override
	public Category findCategory(String categoryName) {
		
		try {
			return this.categoryDao.findCategory(categoryName);
			
		} catch (DaoException e) {
			log.error("Could not retrieve category by name.",e);
		}
		return null;
	}

	@Override
	public Category saveCategory(Category category) throws CdCatalogueException {
		
		try {
			category = this.categoryDao.createCategory(category);
			this.notifyObservers(new NewCategoryNotification(category));
		
		} catch (DuplicateNameException e) {
			throw new InvalidNameException("Category name not allowed.",e);
			
		} catch (DaoException e) {
			throw new CdCatalogueException("Could not save category " + category, e);
		}
		
		return category;
		
	}
	
	private void notifyObservers(NotificationCommand<? extends Object> notificationCommand) {		
		notificationCommand.sendNotification(this.getObservers());
	}

	private List<CdCatalogueModelObserver> getObservers() {
		return this.observers;
	}

	public void catalogue(File path, Category category, String volumeName) 
	throws CdCatalogueException {
		
		try {
			Volume volume = volumeDao.findVolume(volumeName);
			if (volume != null) {
				throw new InvalidNameException("Volume " + volumeName + " already exists.");
			}
			
			volume = new Volume(category,volumeName);
			volume.setResources(new LinkedList<Resource>());
			
			this.catalogue(path, volume);
			
			this.volumeDao.createVolume(volume);
			
			this.notifyObservers(new NewVolumeNotification(volume));
			
			
		} catch (DaoException e) {
			throw new CdCatalogueException("Could not catalogue path.", e);
		} 
	}
	

	private void catalogue(File path, Volume volume) {
		
		Queue<File> cola = new LinkedList<File>();
		cola.add(path);
		
		while (!cola.isEmpty()) {
			
			File toProcess = cola.poll();
			File[] files = toProcess.listFiles();
			
			if (files == null) {
				
				Resource resource = new Resource();
				resource.setPath(toProcess.getParent());
				resource.setResourceName(toProcess.getName()); 
				resource.setResourceType(this.getExtension(toProcess.getName()));
				resource.setFileSize(this.getFileSize(toProcess.length()));
				resource.setModifiedDate(new Date(toProcess.lastModified()));

				resource.setVolume(volume);
				
				volume.getResources().add(resource);

			} else {
				for (File file : files) {
					cola.add(file);
				}
			}
		}
	}

	
	private String getFileSize(long length) {
		
		String value = null;
		
		if (length < 1024) {
			value = length + " B";
		
		} else {
		
			DecimalFormat format = new DecimalFormat();
			format.setMaximumFractionDigits(3);
			
			double kBytes = (double)length / 1024;
			
			if (kBytes < 1024) {
				value = format.format(kBytes) + " KB";
			
			} else {
				double mBytes = kBytes / 1024;			
				value = format.format(mBytes) + " MB";
				
				if (value.length() > 50) {
					double gBytes = mBytes / 1024;	
					value = format.format(gBytes) + " GB";
				}
			}
		}
		
		return value;
	}

	private String getExtension(String fileName) {
		
		int index = fileName.lastIndexOf(".");
		if (index > 0) {
			return fileName.substring(index + 1);
		}
		
		return null;
	}

	protected String getFileName(String fileName) {
		
		int index = fileName.lastIndexOf(".");
		if (index > 0) {
			return fileName.substring(0,index);
		}
		
		return fileName;
	}

	public void deleteCategory(Category category) throws CdCatalogueException {
		
		try {
			categoryDao.deleteCategory(category);
			this.notifyObservers(
					new CategoryDeletedNotification(category));
			
		} catch (DaoException e) {
			throw new CdCatalogueException("Could not delete category.", e);
		}
		
	}

	@Override
	public void updateCategory(Category category) throws CdCatalogueException {
		
		try {
			categoryDao.updateCategory(category);
			this.notifyObservers(new CategoryUpdatedNotification(category));
						
		} catch (DaoException e) {
			throw new CdCatalogueException("Could not update category.", e);
		}
		
	}

	@Override
	public void updateVolume(Volume volume) throws CdCatalogueException {
		
		try {
			volumeDao.updateVolume(volume);
			this.notifyObservers(new VolumeUpdatedNotification(volume));
			
		} catch (DuplicateNameException e) {
			throw new InvalidNameException("Category name not allowed.",e);
			
		} catch (DaoException e) {
			throw new CdCatalogueException("Could not update volume.", e);
		}
		
	}

	@Override
	public void updateVolumes(List<Volume> volumes) throws CdCatalogueException {
		
		try {
			volumeDao.updateVolumes(volumes);
			this.notifyObservers(new VolumesUpdatedNotification(volumes));
			
		} catch (DaoException e) {
			throw new CdCatalogueException("Could not update volumes.", e);
		}		
	}

	@Override
	public void shutDownApp() {
		DaoResources.getInstance().shutDown();		
	}

	@Override
	public Volume findVolume(String volumeName) {
		
		Volume volume = null;
		
		try {
			volume = volumeDao.findVolume(volumeName);
			
		} catch (DaoException e) {
			log.error("Could not find volume " + volumeName, e);
		}
		
		return volume;
	}

	@Override
	public void deleteVolume(Volume volume) throws CdCatalogueException {
		
		try {
			volumeDao.deleteVolume(volume);
			this.notifyObservers(new VolumeDeletedNotification(volume));
			
		} catch (DaoException e) {
			throw new CdCatalogueException("Could not delete volume.", e);
		}
		
	}

	@Override
	public Category getDefaultCategory() {
		
		Category defaultCategory = null;		
			
		try {		
			defaultCategory = this.categoryDao.findCategory(Configuration.DEFAULT_CATEGORY_NAME);
			
			if (defaultCategory == null) {
				defaultCategory = this.saveCategory(
						new Category(Configuration.DEFAULT_CATEGORY_NAME));				
			}
			
		} catch (Exception e) {
			log.error("Could not create default category.", e);
		}
		
		return defaultCategory;
	}

}
