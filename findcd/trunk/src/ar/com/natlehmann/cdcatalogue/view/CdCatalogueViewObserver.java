package ar.com.natlehmann.cdcatalogue.view;

import java.io.File;

import ar.com.natlehmann.cdcatalogue.business.model.Category;

public interface CdCatalogueViewObserver {
	
	void fireSearch();
	void fireCleanSearch();
	
	void fireChooseFile();
	
	void fireShowFirstPage();
	void fireShowNextPage();
	void fireShowLastPage();
	void fireShowPreviousPage();
	
	void fireAddCategory(String categoryName, CdCatalogueViewFacade view);
	void fireCatalogue(File path, Category category, String volumeName);
	
	void fireLaunchEditVolume(String volumeName);	
	void fireLaunchEditCategory(String categoryName);
	void fireEditCategory(String categoryName, CdCatalogueViewFacade view);
	
	void fireDeleteVolume(String volumeName);
	void fireDeleteCategory(String categoryName);

}
