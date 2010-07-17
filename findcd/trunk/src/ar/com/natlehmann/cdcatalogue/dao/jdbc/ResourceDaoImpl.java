package ar.com.natlehmann.cdcatalogue.dao.jdbc;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.NotImplementedException;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.ResourceDao;

public class ResourceDaoImpl implements ResourceDao {

	public void createResource(Resource resource) throws DaoException {
		
		try {
			
			Connection connection = DaoConnection.getInstance().getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
					"INSERT INTO Resource (volumeId,resourceName,path,resourceType,fileSize,modifiedDate,comments) " +
			"VALUES (?,?,?,?,?,?,?)");
			
			statement.setInt(1, resource.getVolume().getVolumeId());
			statement.setString(2, resource.getResourceName());
			statement.setString(3, resource.getPath());
			statement.setString(4, resource.getResourceType());
			statement.setString(5, resource.getFileSize());
			statement.setDate(6, new Date(resource.getModifiedDate().getTime()));
			statement.setString(7, resource.getComments());
			
			statement.executeUpdate();
			
			statement.close();
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}

	}

	@Override
	public List<Resource> getResources(List<Parameter> parameters,
			OrderBy orderField, Page page) throws DaoException {
		throw new NotImplementedException("Not implemented");
	}

	@Override
	public List<Resource> getResources(List<Parameter> parameters)
			throws DaoException {
		throw new NotImplementedException("Not implemented");
	}

	@Override
	public List<Resource> getResources(List<Parameter> parameters,
			OrderBy orderBy) throws DaoException {
		throw new NotImplementedException("Not implemented");
	}

	@Override
	public long getResourceCount(List<Parameter> searchParameters) throws DaoException {
		throw new NotImplementedException("Not implemented");
	}
	
//	public static void main(String[] args) {
//		ResourceDaoImpl r = new ResourceDaoImpl();
//		Resource resource = new Resource();
//		resource.setComments("nada");
//		resource.setFileSize("tamanio");
//		resource.setModifiedDate(new java.util.Date());
//		resource.setPath("mi path");
//		resource.setResourceName("nombre del recurso");
//		resource.setResourceType("tipo");
//		resource.setVolume(new Volume(1));
//		
//		try {
//			r.createResource(resource);
//		} catch (DaoException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

}
