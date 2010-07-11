package ar.com.natlehmann.cdcatalogue.controller;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueBusinessModel;
import ar.com.natlehmann.cdcatalogue.business.exception.CdCatalogueException;
import ar.com.natlehmann.cdcatalogue.business.exception.InvalidNameException;
import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.SearchField;
import ar.com.natlehmann.cdcatalogue.util.Validator;
import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;
import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewObserver;

public class CdCatalogueControllerImpl implements CdCatalogueControllerFacade, CdCatalogueViewObserver {
	
	private static Log log = LogFactory.getLog(CdCatalogueControllerImpl.class);
	
	private CdCatalogueViewFacade viewFacade;
	private CdCatalogueBusinessModel modelFacade;

	private String oldCategoryName;
	
	public CdCatalogueControllerImpl() {
	}
	
	@SuppressWarnings("unused")
	private void init() {
		this.viewFacade.setViewObserver(this);
	}

	public CdCatalogueViewFacade getViewFacade() {
		return viewFacade;
	}

	public void setViewFacade(CdCatalogueViewFacade viewFacade) {
		this.viewFacade = viewFacade;
	}

	public CdCatalogueBusinessModel getModelFacade() {
		return modelFacade;
	}

	public void setModelFacade(CdCatalogueBusinessModel modelFacade) {
		this.modelFacade = modelFacade;
	}
	
	private void cleanUp() {
		viewFacade.clearMessages();
	}

	@Override
	public void fireChooseFile() {
		this.cleanUp();
		viewFacade.chooseFile();
		
	}

	@Override
	public void fireCleanSearch() {
		this.cleanUp();
		viewFacade.cleanSearchFields();
		viewFacade.cleanTreeSelection();
		viewFacade.refreshResults();		
	}

	@Override
	public void fireSearch() {
		this.cleanUp();
		viewFacade.refreshResults();
		
	}

	@Override
	public void fireShowFirstPage() {
		this.cleanUp();
		viewFacade.showFirstPage();
		
	}

	@Override
	public void fireShowLastPage() {
		this.cleanUp();
		viewFacade.showLastPage();
		
	}

	@Override
	public void fireShowNextPage() {
		this.cleanUp();
		viewFacade.showNextPage();
		
	}

	@Override
	public void fireShowPreviousPage() {
		this.cleanUp();
		viewFacade.showPreviousPage();
		
	}

	@Override
	public void startApp() {
		viewFacade.init();
		
	}

	@Override
	public void fireAddCategory(String categoryName, CdCatalogueViewFacade view) {
		
		this.cleanUp();
		if (Validator.isNull(categoryName)) {
			view.showErrorMessage("You must enter a category name.");
		
		} else {
			Category category = modelFacade.findCategory(categoryName);
			if (category != null) {
				view.showErrorMessage("That category name already exists.");
			
			} else {
				
				try {
					category = new Category(categoryName);
					modelFacade.saveCategory(category);
					
					view.addCategoryItem(category);
					view.showSuccessMessage("The category was successfully created.");
				
				} catch (CdCatalogueException e) {
					log.error("Could not save category.", e);
					view.showErrorMessage("Could not save the category.");
				}
			}
		}
		
	}

	@Override
	public void fireCatalogue(File path, Category category, String volumeName) {
		
		try {
			modelFacade.catalogue(path, category, volumeName);
			
			viewFacade.closeDialogues();
			viewFacade.cleanSearchFields();
			viewFacade.filterResultsByVolumeCategory(category.getCategoryName(), volumeName);
			viewFacade.addVolumeTreeNode(category, volumeName);
			viewFacade.showSuccessMessage("The path was processed successfully.");

			
		} catch (InvalidNameException e) {
			log.error("Volume name is already taken.");
			log.debug(e);
			viewFacade.showErrorMessage("Volume name is already taken.");
		
		} catch (CdCatalogueException e) {
			log.error("There was an error processing the selected path.", e);
			viewFacade.showErrorMessage(
					"There was an error processing the selected path. Please try again.");
		}
		
	}

	@Override
	public void fireDeleteCategory(String categoryName) {
		// TODO IMPLEMENTAR!!!
		System.out.println("CdCatalogueControllerImpl.fireDeleteCategory()");
		
	}

	@Override
	public void fireDeleteVolume(String volumeName) {
		// TODO IMPLEMENTAR!!!
		System.out.println("CdCatalogueControllerImpl.fireDeleteVolume()");
		
	}

	@Override
	public void fireLaunchEditCategory(String categoryName) {
		this.oldCategoryName = categoryName;
		this.viewFacade.showEditCategoryDialog(categoryName);
		
	}

	@Override
	public void fireLaunchEditVolume(String volumeName) {
		// TODO IMPLEMENTAR!!!
		System.out.println("CdCatalogueControllerImpl.fireEditVolume()");
		
	}

	@Override
	public void fireEditCategory(String categoryName, CdCatalogueViewFacade view) {
		
		if (Validator.isNull(categoryName)) {
			view.showErrorMessage("Category name cannot be empty");
		
		} else {
			if (categoryName.equalsIgnoreCase(oldCategoryName)) {
				view.showErrorMessage("You must enter a different category name");
				
			} else {
				
				try {
				
					Category existentCategory = this.modelFacade.findCategory(categoryName);
					Category oldCategory = this.modelFacade.findCategory(oldCategoryName);
					
					if (existentCategory != null) {					
						
						if (view.isConfirmed("Category " + categoryName 
								+ " already exists. Do you want to merge volumes belonging to " 
								+ this.oldCategoryName + " with the ones belonging to " 
								+ categoryName + "?")) {
						
							List<Volume> volumes = this.modelFacade.getVolumesByCategory(oldCategory);
							for (Volume volume : volumes) {
								volume.setCategory(existentCategory);
							}
							
							this.modelFacade.updateVolumes(volumes);
							this.modelFacade.deleteCategory(oldCategory);
							
							this.viewFacade.closeDialogues();
							this.viewFacade.mergeCategories(oldCategory, existentCategory);
							
							this.viewFacade.showSuccessMessage("Volumes were merged successfully and category " 
									+ oldCategoryName + " was deleted.");
						}
						
					} else {
						
						oldCategory.setCategoryName(categoryName);						
						this.modelFacade.updateCategory(oldCategory);
//System.out.println("ACA VA -------------------- ");
//List<Parameter> par = new LinkedList<Parameter>();
//par.add(new Parameter(SearchField.CATEGORY_NAME,categoryName));
//for(Resource r : this.modelFacade.getResources(par, null, new Page(1))) System.out.println("categoria " + r.getVolume().getCategory());
						this.viewFacade.closeDialogues();
						this.viewFacade.refreshCategory(oldCategoryName, categoryName);
						this.viewFacade.showSuccessMessage("The category was successfully updated.");
						
					}
					
				} catch (CdCatalogueException e) {
					log.error("There was an error updating the category.", e);
					viewFacade.showErrorMessage(
							"There was an error updating the category. Please try again.");
				}
			}
		}
		
	}

}
