package ar.com.natlehmann.cdcatalogue.business.notification;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelObserver;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;

public class VolumeDeletedNotification extends NotificationCommand<Volume> {

	public VolumeDeletedNotification(Volume volume) {
		super(volume);
	}

	@Override
	protected void notifyObserver(CdCatalogueModelObserver observer) {
		observer.volumeDeleted(this.getModelEvent());
	}

}
