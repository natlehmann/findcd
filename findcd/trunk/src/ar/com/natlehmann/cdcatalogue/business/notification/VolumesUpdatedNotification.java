package ar.com.natlehmann.cdcatalogue.business.notification;

import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelObserver;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;

public class VolumesUpdatedNotification extends NotificationCommand<List<Volume>> {

	public VolumesUpdatedNotification(List<Volume> volumes) {
		super(volumes);
	}

	@Override
	protected void notifyObserver(CdCatalogueModelObserver observer) {
		observer.volumesUpdated(this.getModelEvent());		
	}

}
