package ar.com.natlehmann.cdcatalogue.view.action;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewFacade;
import ar.com.natlehmann.cdcatalogue.view.CdCatalogueViewObserver;

public abstract class CommandActionListener implements ActionListener {
	
	protected CdCatalogueViewObserver viewObserver;
	private CdCatalogueViewFacade viewFacade;
	
	protected CommandActionListener(CdCatalogueViewFacade viewFacade) {
		this.viewObserver = viewFacade.getViewObserver();
		this.viewFacade = viewFacade;
	}
	
	public CdCatalogueViewObserver getViewObserver() {
		return this.viewObserver;
	}
	
	protected CdCatalogueViewFacade getViewFacade() {
		return viewFacade;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		
		getViewFacade().clearMessages();
		this.execute(event);
	}
	
	public abstract void execute(ActionEvent event);

}
