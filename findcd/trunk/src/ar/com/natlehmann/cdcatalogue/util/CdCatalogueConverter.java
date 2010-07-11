package ar.com.natlehmann.cdcatalogue.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import ar.com.natlehmann.cdcatalogue.business.model.Category;
import ar.com.natlehmann.cdcatalogue.business.model.Resource;
import ar.com.natlehmann.cdcatalogue.business.model.Volume;
import ar.com.natlehmann.cdcatalogue.dao.CategoryDao;
import ar.com.natlehmann.cdcatalogue.dao.DaoException;
import ar.com.natlehmann.cdcatalogue.dao.ResourceDao;
import ar.com.natlehmann.cdcatalogue.dao.VolumeDao;
import ar.com.natlehmann.cdcatalogue.dao.jdbc.CategoryDaoImpl;
import ar.com.natlehmann.cdcatalogue.dao.jdbc.DaoConnection;
import ar.com.natlehmann.cdcatalogue.dao.jdbc.ResourceDaoImpl;
import ar.com.natlehmann.cdcatalogue.dao.jdbc.VolumeDaoImpl;

public class CdCatalogueConverter {
	
	private CategoryDao categoryDao = new CategoryDaoImpl();
	private VolumeDao volumeDao = new VolumeDaoImpl();
	private ResourceDao resourceDao = new ResourceDaoImpl();
	
	
	public void convert(String fileName, String separator) 
	throws IOException, ParseException, SQLException, DaoException {
		
		try {
			File file = new File(fileName);
			
			if (!file.exists() || !file.canRead()) {
				throw new IllegalArgumentException("Invalid file. Cannot read " + fileName);
			}
			
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss aa");
			
			BufferedReader reader = new BufferedReader(new FileReader(fileName));
			String line = reader.readLine();
			
			while(line != null) {
				
				String[] fields = line.split(separator);
				Resource resource = new Resource();
				resource.setPath(fields[0]);
				resource.setResourceName(fields[1]);
				resource.setResourceType(fields[2]);
				resource.setFileSize(fields[3]);
				
				String date = fields[4].replace("p.m.", "pm").replace("a.m.", "am");
				resource.setModifiedDate(format.parse(date));
				resource.setComments(fields[6]);
				
				String volumeName = fields[7];
				String categoryName = fields[5];
				
				Volume volume = volumeDao.findVolume(volumeName);
				if (volume == null) {
					
					//if the volume doesn't exist, we look for the category first
					Category category = null;
					if (categoryName != null && !categoryName.trim().equals("")) {
						category = categoryDao.findCategory(categoryName);
						
						// if it doesn't exist, we create it
						if (category == null) {
							category = new Category();
							category.setCategoryName(categoryName);
							category = categoryDao.createCategory(category);
						}				
					}			
					
					volume = new Volume();
					volume.setVolumeName(volumeName);
					volume.setCategory(category);
					
					volume = volumeDao.createVolume(volume);
				}
				
				resource.setVolume(volume);
				resourceDao.createResource(resource);
				
				line = reader.readLine();
			}
			
			reader.close();
			
		} finally {
			DaoConnection.getInstance().terminate();
			System.out.println("Releasing connection.");
		}
	}
	
	public static void main(String[] args) {
		
		System.out.println("Processing file " + args[0]);
		System.out.println("with separator " + args[1]);
		
		CdCatalogueConverter converter = new CdCatalogueConverter();
		
		try {
			converter.convert(args[0], args[1]);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	

}
