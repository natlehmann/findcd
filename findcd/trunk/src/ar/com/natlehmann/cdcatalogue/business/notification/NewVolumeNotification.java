package ar.com.natlehmann.cdcatalogue.business.notification;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelObserver;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;

public class NewVolumeNotification extends NotificationCommand<Volume> {

	public NewVolumeNotification(Volume modelEvent) {
		super(modelEvent);
	}

	@Override
	protected void notifyObserver(CdCatalogueModelObserver observer) {
		observer.volumeCreated(this.getModelEvent());		
	}

}
