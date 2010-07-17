package ar.com.natlehmann.cdcatalogue.business;

import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;

public interface CdCatalogueModelObserver {

	void categoryCreated(Category newCategory);
	void categoryUpdated(Category category);
	void categoryDeleted(Category category);

	void volumeCreated(Volume newVolume);
	void volumesUpdated(List<Volume> volumes);
	void volumeDeleted(Volume volume);
	void volumeUpdated(Volume volume);


}
