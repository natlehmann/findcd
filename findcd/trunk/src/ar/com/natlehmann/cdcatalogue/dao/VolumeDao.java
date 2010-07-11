package ar.com.natlehmann.cdcatalogue.dao;

import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;

public interface VolumeDao {
	
	Volume createVolume(Volume volume) throws DaoException;
	
	Volume getVolume(Integer volumeId) throws DaoException;
	
	Volume findVolume(String volumeName) throws DaoException;
	
	List<Volume> getVolumes(List<Parameter> parameters) 
	throws DaoException;
	
	List<Volume> getVolumes(List<Parameter> parameters, OrderBy orderField) 
	throws DaoException;
	
	List<Volume> getVolumes(List<Parameter> parameters, OrderBy orderField, Page page) 
	throws DaoException;
	
	List<Volume> getVolumes(Page page) throws DaoException;
	
	List<Volume> getVolumes(OrderBy orderField, Page page) 
	throws DaoException;
	
	List<Volume> getVolumes(Category category) throws DaoException;

	void deleteVolume(Volume volume) throws DaoException;
	
	void updateVolume(Volume volume) throws DaoException;
	void updateVolumes(List<Volume> volumes) throws DaoException;

}
