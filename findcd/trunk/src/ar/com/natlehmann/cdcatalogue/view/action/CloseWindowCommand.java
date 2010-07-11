package ar.com.natlehmann.cdcatalogue.view.action;

import java.awt.Window;
import java.awt.event.ActionEvent;

import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;

public class CloseWindowCommand extends CommandActionListener {

	private Window window;
	
	public CloseWindowCommand(CdCatalogueViewFacade viewFacade, Window window) {
		super(viewFacade);
		this.window = window;
	}

	@Override
	public void execute(ActionEvent event) {
		
		this.window.dispose();
	}

}
