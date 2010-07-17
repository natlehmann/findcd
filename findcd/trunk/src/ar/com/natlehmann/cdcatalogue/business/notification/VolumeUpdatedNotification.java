package ar.com.natlehmann.cdcatalogue.business.notification;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelObserver;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;

public class VolumeUpdatedNotification extends NotificationCommand<Volume> {

	public VolumeUpdatedNotification(Volume volume) {
		super(volume);
	}

	@Override
	protected void notifyObserver(CdCatalogueModelObserver observer) {
		observer.volumeUpdated(this.getModelEvent());
	}

}
