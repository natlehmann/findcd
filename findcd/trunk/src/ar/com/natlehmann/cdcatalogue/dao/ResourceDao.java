package ar.com.natlehmann.cdcatalogue.dao;

import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.model.Resource;

public interface ResourceDao {
	
	void createResource(Resource resource) throws DaoException;
	
	List<Resource> getResources(List<Parameter> parameters, OrderBy orderField, Page page) 
	throws DaoException;

	List<Resource> getResources(List<Parameter> parameters) throws DaoException;
	
	List<Resource> getResources(List<Parameter> parameters, OrderBy orderBy)
	throws DaoException;

	long getResourceCount(List<Parameter> searchParameters) throws DaoException;

}
