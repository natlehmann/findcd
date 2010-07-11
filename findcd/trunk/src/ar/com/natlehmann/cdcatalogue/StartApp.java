package ar.com.natlehmann.cdcatalogue;

import ar.com.natlehmann.cdcatalogue.controller.CdCatalogueControllerFacade;

public class StartApp {
	
	public static void main(String[] args) {
		
		CdCatalogueControllerFacade controller = BeanLocator.instance().getCdCatalogueControllerFacade();
		controller.startApp();
		
	}

}
