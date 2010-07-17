package ar.com.natlehmann.cdcatalogue.business.notification;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelObserver;
import ar.com.natlehmann.cdcatalogue.business.model.Category;

public class NewCategoryNotification extends NotificationCommand<Category> {

	public NewCategoryNotification(Category modelEvent) {
		super(modelEvent);
	}

	@Override
	protected void notifyObserver(CdCatalogueModelObserver observer) {
		observer.categoryCreated(this.getModelEvent());		
	}

}
