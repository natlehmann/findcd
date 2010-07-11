package ar.com.natlehmann.cdcatalogue.dao.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.NotImplementedException;
import ar.com.natlehmann.cdcatalogue.dao.OrderBy;
import ar.com.natlehmann.cdcatalogue.dao.Page;
import ar.com.natlehmann.cdcatalogue.dao.Parameter;
import ar.com.natlehmann.cdcatalogue.dao.VolumeDao;

public class VolumeDaoImpl implements VolumeDao {

	public Volume createVolume(Volume volume) throws DaoException {
		
		try {
			
			Connection connection = DaoConnection.getInstance().getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
			"INSERT INTO Volume (categoryId,volumeName) VALUES (?,?)");
			
			if (volume.getCategory() != null) {
				statement.setInt(1, volume.getCategory().getCategoryId());
				
			} else {
				statement.setObject(1, null);
			} 
			
			statement.setString(2, volume.getVolumeName());			
			int rowsAffected = statement.executeUpdate();
			
			statement.close();
			
			if (rowsAffected == 1) {
				return this.getLastInserted();
				
			} else {
				throw new DaoException("Could not insert Volume " + volume);
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}

	}
	
	
	private Volume getLastInserted() throws DaoException {
		
		try {
			
			Connection connection = DaoConnection.getInstance().getConnection();
			
			Statement statement = connection.createStatement();
			
			ResultSet result = statement.executeQuery(
			"SELECT * FROM Volume WHERE volumeId = (SELECT MAX(volumeId) FROM Volume)");
			
			List<Volume> results = this.convertResult(result);
			result.close();
			statement.close();
			
			
			if (!results.isEmpty()) {
				return results.iterator().next();
				
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}
	
	
	private List<Volume> convertResult(ResultSet resultSet) throws SQLException {
		
		List<Volume> results = new LinkedList<Volume>();
		
		while (resultSet.next()) {
			Volume volume = new Volume();
			volume.setVolumeId(resultSet.getInt("volumeId"));
			volume.setVolumeName(resultSet.getString("volumeName"));
			
			// TODO: falta relacion con category
			results.add(volume);
		}
		
		return results;
	}


	public Volume getVolume(Integer volumeId) throws DaoException {
		
		try {
			
			Connection connection = DaoConnection.getInstance().getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
			"SELECT * FROM Volume WHERE volumeId = ?");
			
			statement.setInt(1, volumeId);
			ResultSet result = statement.executeQuery();
			
			List<Volume> results = this.convertResult(result);
			result.close();
			statement.close();
			
			if (!results.isEmpty()) {
				return results.iterator().next();
				
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}

	public Volume findVolume(String volumeName) throws DaoException {		

		try {
			
			Connection connection = DaoConnection.getInstance().getConnection();
			
			PreparedStatement statement = connection.prepareStatement(
			"SELECT * FROM Volume WHERE volumeName = ?");
			
			statement.setString(1, volumeName);
			ResultSet result = statement.executeQuery();
			
			List<Volume> results = this.convertResult(result);
			result.close();
			statement.close();
			
			if (!results.isEmpty()) {
				return results.iterator().next();
				
			} else {
				return null;
			}
			
		} catch (SQLException e) {
			throw new DaoException(e);
		}
	}


	public List<Volume> getVolumes(List<Parameter> parameters)
			throws DaoException {
		throw new NotImplementedException("Not implemented");
	}


	@Override
	public List<Volume> getVolumes(List<Parameter> parameters,
			OrderBy orderField) throws DaoException {
		throw new NotImplementedException("Not implemented");
	}


	@Override
	public List<Volume> getVolumes(List<Parameter> parameters,
			OrderBy orderField, Page page) throws DaoException {
		throw new NotImplementedException("Not implemented");
	}


	@Override
	public List<Volume> getVolumes(Page page) throws DaoException {
		throw new NotImplementedException("Not implemented");
	}


	@Override
	public List<Volume> getVolumes(OrderBy orderField, Page page)
			throws DaoException {
		throw new NotImplementedException("Not implemented");
	}


	@Override
	public List<Volume> getVolumes(Category category) throws DaoException {
		throw new NotImplementedException("Not implemented");
	}


	@Override
	public void deleteVolume(Volume found) throws DaoException {
		throw new NotImplementedException("Not implemented");		
	}


	@Override
	public void updateVolume(Volume volume) throws DaoException {
		throw new NotImplementedException("Not implemented");		
	}


	@Override
	public void updateVolumes(List<Volume> volumes) throws DaoException {
		throw new NotImplementedException("Not implemented");		
	}
	
	
//	public static void main(String[] args) {
//		VolumeDaoImpl dao = new VolumeDaoImpl();
//		
//		Volume volume = new Volume();
//		volume.setVolumeName("nombre del volumen");
//		try {
//			dao.createVolume(volume);
//			
//			volume.setCategory(new Category(1));
//			dao.createVolume(volume);
//			
//			System.out.println(dao.getVolume(1));
//			
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (DaoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
