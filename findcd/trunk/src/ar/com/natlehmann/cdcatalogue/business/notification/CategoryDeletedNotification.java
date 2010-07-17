package ar.com.natlehmann.cdcatalogue.business.notification;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelObserver;
import ar.com.natlehmann.cdcatalogue.business.model.Category;

public class CategoryDeletedNotification extends NotificationCommand<Category> {

	public CategoryDeletedNotification(Category category) {
		super(category);
	}

	@Override
	protected void notifyObserver(CdCatalogueModelObserver observer) {
		observer.categoryDeleted(this.getModelEvent());		
	}

}
