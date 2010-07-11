package ar.com.natlehmann.cdcatalogue;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelFacade;
import ar.com.natlehmann.cdcatalogue.controller.CdCatalogueControllerFacade;
import ar.com.natlehmann.cdcatalogue.dao.CategoryDao;
import ar.com.natlehmann.cdcatalogue.dao.ResourceDao;
import ar.com.natlehmann.cdcatalogue.dao.VolumeDao;
import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;

public class BeanLocator {
	
	private static final BeanLocator instance = new BeanLocator();

	private ApplicationContext context;
	
	public static BeanLocator instance() {
		return instance;
	}
	
	protected BeanLocator() {
		context = new ClassPathXmlApplicationContext(Configuration.APPLICATION_CONTEXT_PATH);
	}
	
	public CategoryDao getCategoryDao() {
		return (CategoryDao)this.context.getBean("categoryDao");
	}
	
	public ResourceDao getResourceDao() {
		return (ResourceDao)this.context.getBean("resourceDao");
	}
	
	public VolumeDao getVolumeDao() {
		return (VolumeDao)this.context.getBean("volumeDao");
	}
	
	public CdCatalogueModelFacade getCdCatalogueModelFacade() {
		return (CdCatalogueModelFacade)this.context.getBean("cdCatalogueModelFacade");
	}

	public CdCatalogueViewFacade getCdCatalogueViewFacade() {
		return (CdCatalogueViewFacade)this.context.getBean("cdCatalogueViewFacade");
	}

	public CdCatalogueControllerFacade getCdCatalogueControllerFacade() {
		return (CdCatalogueControllerFacade)this.context.getBean("cdCatalogueControllerFacade");
	}

}
