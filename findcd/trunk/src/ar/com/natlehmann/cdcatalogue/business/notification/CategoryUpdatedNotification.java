package ar.com.natlehmann.cdcatalogue.business.notification;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelObserver;
import ar.com.natlehmann.cdcatalogue.business.model.Category;

public class CategoryUpdatedNotification extends NotificationCommand<Category> {

	public CategoryUpdatedNotification(Category category) {
		super(category);
	}

	@Override
	protected void notifyObserver(CdCatalogueModelObserver observer) {
		observer.categoryUpdated(this.getModelEvent());
	}

}
