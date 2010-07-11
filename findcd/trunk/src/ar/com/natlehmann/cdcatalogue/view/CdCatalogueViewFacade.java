package ar.com.natlehmann.cdcatalogue.view;

import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;

public interface CdCatalogueViewFacade {
	
	void init();
	
	List<Parameter> getSearchValues();
	
	OrderBy getOrderByField();
	
	Page getCurrentPage();
	
	void refreshResults();
	
	void cleanSearchFields();
	void cleanTreeSelection();

	void showNextPage();
	void showPreviousPage();
	void showFirstPage();
	void showLastPage();

	void filterResultsByVolumeCategory(String categoryName, String volumeName);
	void filterResultsByCategoryName(String categoryName);
	
	void chooseFile();
	
	void showErrorMessage(String message);
	void showSuccessMessage(String message);
	void clearMessages();
	
	void setViewObserver(CdCatalogueViewObserver viewObserver);
	CdCatalogueViewObserver getViewObserver();

	void addCategoryItem(Category newCategory);
	void addVolumeTreeNode(Category category, String volumeName);
	void mergeCategories(Category oldCategory, Category existentCategory);
	void refreshCategory(String oldCategoryName, String newCategoryName);
	
	void closeDialogues();

	boolean isConfirmed(String message);

	void showEditCategoryDialog(String categoryName);






}
