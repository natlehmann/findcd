package ar.com.natlehmann.cdcatalogue.view.listener;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;
import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewObserver;

public class ShutDownAppListener extends WindowAdapter {
	
	protected CdCatalogueViewObserver viewObserver;
	
	@SuppressWarnings("unused")
	private CdCatalogueViewFacade viewFacade;

	public ShutDownAppListener(CdCatalogueViewFacade viewFacade) {
		this.viewObserver = viewFacade.getViewObserver();
		this.viewFacade = viewFacade;
	}
	
	@Override
	public void windowClosing(WindowEvent e) {
		this.viewObserver.fireShutDownApp();
	}

}
