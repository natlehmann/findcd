package ar.com.natlehmann.cdcatalogue.view.action;

import java.awt.event.ActionEvent;

import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;

public class NextPageCommand extends CommandActionListener {

	public NextPageCommand(CdCatalogueViewFacade viewFacade) {
		super(viewFacade);
	}

	@Override
	public void execute(ActionEvent event) {
		
		getViewObserver().fireShowNextPage();

	}

}
