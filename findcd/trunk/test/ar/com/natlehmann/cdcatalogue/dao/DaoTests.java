package ar.com.natlehmann.cdcatalogue.dao;

import junit.framework.JUnit4TestAdapter;
import junit.framework.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import ar.com.natlehmann.cdcatalogue.dao.jpa.CategoryDaoTest;
import ar.com.natlehmann.cdcatalogue.dao.jpa.ResourceCategoryDaoTest;
import ar.com.natlehmann.cdcatalogue.dao.jpa.ResourceDaoTest;
import ar.com.natlehmann.cdcatalogue.dao.jpa.VolumeDaoTest;

/**
 * To run these tests you must first run the script sql/InsertTestData.sql on the DB
 * @author natalia
 *
 */
@RunWith(Suite.class)
@SuiteClasses( { PageTest.class, CategoryDaoTest.class, 
	ResourceDaoTest.class, VolumeDaoTest.class, ResourceCategoryDaoTest.class })
public class DaoTests {

	public static Test suite() {
		return new JUnit4TestAdapter(DaoTests.class);
	}

}
