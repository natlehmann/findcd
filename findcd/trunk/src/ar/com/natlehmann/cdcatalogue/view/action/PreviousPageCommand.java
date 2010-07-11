package ar.com.natlehmann.cdcatalogue.view.action;

import java.awt.event.ActionEvent;

import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;

public class PreviousPageCommand extends CommandActionListener {

	public PreviousPageCommand(CdCatalogueViewFacade viewFacade) {
		super(viewFacade);
	}

	@Override
	public void execute(ActionEvent event) {
		
		getViewObserver().fireShowPreviousPage();

	}

}
