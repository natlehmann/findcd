package ar.com.natlehmann.cdcatalogue.business;

import java.io.File;
import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.exception.CdCatalogueException;
import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;

public interface CdCatalogueBusinessModel extends CdCatalogueModelFacade {
	
	Category findCategory(String categoryName);
	Category saveCategory(Category category) throws CdCatalogueException;	
	void deleteCategory(Category category) throws CdCatalogueException;
	void updateCategory(Category category) throws CdCatalogueException;
	
	void catalogue(File path, Category category, String volumeName) throws CdCatalogueException;
	
	Volume findVolume(String volumeName);
	void updateVolume(Volume volume) throws CdCatalogueException;
	void updateVolumes(List<Volume> volumes) throws CdCatalogueException;
	void deleteVolume(Volume volume) throws CdCatalogueException;

	void shutDownApp();

	

}
