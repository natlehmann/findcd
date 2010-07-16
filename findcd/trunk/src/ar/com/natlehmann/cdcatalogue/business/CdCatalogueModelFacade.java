package ar.com.natlehmann.cdcatalogue.business;

import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;

public interface CdCatalogueModelFacade {
	
	List<Volume> getVolumes(List<Parameter> parameters, OrderBy orderField, Page page);

	List<Resource> getResources(List<Parameter> parameters,
			OrderBy orderField, Page page);

	long getResourceCount(List<Parameter> searchParameters);
	
	List<Category> getCategories();
	
	List<Volume> getVolumesByCategory(Category category);
	
	void addObserver(CdCatalogueModelObserver observer);

}
