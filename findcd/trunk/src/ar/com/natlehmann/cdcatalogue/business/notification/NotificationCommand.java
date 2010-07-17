package ar.com.natlehmann.cdcatalogue.business.notification;

import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.CdCatalogueModelObserver;

public abstract class NotificationCommand<T> {

	private T modelEvent;
	
	public NotificationCommand(T modelEvent) {
		this.modelEvent = modelEvent;
	}

	public T getModelEvent() {
		return modelEvent;
	}
	
	public void sendNotification(List<CdCatalogueModelObserver> observers) {
		
		for(CdCatalogueModelObserver observer : observers) {
			this.notifyObserver(observer);
		}
	}

	protected abstract void notifyObserver(CdCatalogueModelObserver observer);
}
