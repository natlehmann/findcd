package ar.com.natlehmann.cdcatalogue.view.action;

import java.awt.event.ActionEvent;

import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;

public class SearchCommand extends CommandActionListener {

	public SearchCommand(CdCatalogueViewFacade viewFacade) {
		super(viewFacade);
	}

	@Override
	public void execute(ActionEvent event) {
		
		getViewObserver().fireSearch();

	}

}
